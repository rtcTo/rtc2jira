package to.rtc.rtc2jira.exporter.jira;

import static to.rtc.rtc2jira.storage.Field.of;
import static to.rtc.rtc2jira.storage.FieldNames.WORK_ITEM_TYPE;
import static to.rtc.rtc2jira.storage.WorkItemTypes.BUSINESSNEED;
import static to.rtc.rtc2jira.storage.WorkItemTypes.DEFECT;
import static to.rtc.rtc2jira.storage.WorkItemTypes.EPIC;
import static to.rtc.rtc2jira.storage.WorkItemTypes.STORY;
import static to.rtc.rtc2jira.storage.WorkItemTypes.TASK;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ws.rs.core.MediaType;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueAttachment;
import to.rtc.rtc2jira.exporter.jira.entities.IssueComment;
import to.rtc.rtc2jira.exporter.jira.entities.IssueFields;
import to.rtc.rtc2jira.exporter.jira.entities.IssueMetadata;
import to.rtc.rtc2jira.exporter.jira.entities.IssueType;
import to.rtc.rtc2jira.exporter.jira.entities.JiraUser;
import to.rtc.rtc2jira.exporter.jira.entities.Project;
import to.rtc.rtc2jira.exporter.jira.mapping.MappingRegistry;
import to.rtc.rtc2jira.storage.Attachment;
import to.rtc.rtc2jira.storage.AttachmentStorage;
import to.rtc.rtc2jira.storage.Comment;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;
import to.rtc.rtc2jira.storage.StorageQuery;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

public class JiraExporter implements Exporter {
  private static final Logger LOGGER = Logger.getLogger(JiraExporter.class.getName());

  private StorageEngine store;
  private Settings settings;
  private JiraRestAccess restAccess;
  private Map<String, List<IssueType>> existingIssueTypes;
  private Optional<Project> projectOptional;
  private int highestExistingId = -1;
  private MappingRegistry mappingRegistry = new MappingRegistry();

  @Override
  public boolean isConfigured() {
    return Settings.getInstance().hasJiraProperties();
  }

  @Override
  public void initialize(Settings settings, StorageEngine store) throws Exception {
    this.settings = settings;
    this.store = store;
    restAccess = new JiraRestAccess(settings.getJiraUrl(), settings.getJiraUser(), settings.getJiraPassword());
    ClientResponse response = restAccess.get("/myself");
    if (response.getStatus() != Status.OK.getStatusCode()) {
      throw new RuntimeException("Unable to connect to jira repository: " + response.toString());
    }
    this.projectOptional = getProject();
  }

  @Override
  public void createOrUpdateItem(ODocument item) throws Exception {
    String workItemId = item.field(FieldNames.ID);
    ensureWorkItemWithId(Integer.parseInt(workItemId));
    Date modified = StorageQuery.getField(item, FieldNames.MODIFIED, Date.from(Instant.now()));
    Date lastExport = StorageQuery.getField(item, FieldNames.JIRA_EXPORT_TIMESTAMP, new Date(0));
    if (Settings.getInstance().isForceUpdate() || modified.compareTo(lastExport) > 0) {
      updateItem(item);
    }
  }

  private void ensureWorkItemWithId(int workItemId) {
    while (highestExistingId < workItemId) {
      createDummyIssues();
    }
  }

  private void createDummyIssues() {
    if (projectOptional.isPresent()) {
      Project project = projectOptional.get();
      Issue issue = new Issue();
      issue.getFields().setProject(project);
      issue.getFields().setIssuetype(getIssueType("Task", project));
      issue.getFields().setSummary("Dummy");
      issue.getFields().setDescription("This is just a dummy issue. Delete it after successfully migrating to Jira.");
      Issue createdIssue = createIssueInJira(issue);
      String highestAsString = createdIssue.getKey().replace(settings.getJiraProjectKey() + '-', "");
      highestExistingId = Integer.parseInt(highestAsString);
    }
  }

  private void updateItem(ODocument item) throws Exception {
    if (projectOptional.isPresent()) {
      Project project = projectOptional.get();
      Issue issue = createIssueFromWorkItem(item, project);
      persistIssue(item, issue);
      persistNewComments(item, issue);
      try {
        persistAttachments(item, issue);
      } catch (IOException e) {
        throw new Exception("Fatal error - could not open attachment directory while exporting", e);
      }
    }
  }

  private void persistIssue(ODocument item, Issue issue) {
    boolean success = updateIssueInJira(issue);
    if (success) {
      storeReference(issue, item);
      storeTimestampOfLastExport(item);
    }
  }

  private void persistAttachments(ODocument item, Issue issue) throws IOException {
    AttachmentStorage storage = new AttachmentStorage();
    String id = item.field(FieldNames.ID);
    List<Attachment> attachments = storage.readAttachments(Long.parseLong(id));
    if (attachments.size() > 0) {
      List<String> alreadyExportedAttachments = item.field(Attachment.EXPORTED_ATTACHMENTS_PROPERTY);
      final FormDataMultiPart multiPart = new FormDataMultiPart();
      int newlyAdded = 0;
      for (Attachment attachment : attachments) {
        // check if already exported
        if (!alreadyExportedAttachments.contains(attachment.getPath().getFileName().toString())) {
          final File fileToUpload = attachment.getPath().toFile();
          if (fileToUpload != null) {
            multiPart.bodyPart(new FileDataBodyPart("file", fileToUpload, MediaType.APPLICATION_OCTET_STREAM_TYPE));
            newlyAdded++;
          }
        }
      }
      if (newlyAdded > 0) {
        ClientResponse clientResponse = restAccess.postMultiPart(issue.getSelfPath() + "/attachments", multiPart);
        if (isResponseOk(clientResponse)) {
          // refresh list of already exported attachments
          List<IssueAttachment> responseAttachments =
              clientResponse.getEntity(new GenericType<List<IssueAttachment>>() {});
          for (IssueAttachment issueAttachment : responseAttachments) {
            alreadyExportedAttachments.add(issueAttachment.getFilename());
          }
          store.setFields(item, //
              of(Attachment.EXPORTED_ATTACHMENTS_PROPERTY, alreadyExportedAttachments));
        }
      }
    }
  }

  private void persistNewComments(ODocument item, Issue issue) {
    List<IssueComment> issueComments = issue.getFields().getComment().getComments();
    List<Comment> comments = item.field(FieldNames.COMMENTS);
    if (comments != null) {
      for (Comment comment : comments) {
        IssueComment issueComment = IssueComment.createWithIdAndBody(issue, comment.getJiraId(), comment.getComment());
        if (comment.getJiraId() == null) {
          JiraUser jiraUser = persistUser(comment);
          issueComment.setAuthor(jiraUser);
          issueComment.setCreated(comment.getDate());
          ClientResponse cr = restAccess.post(issueComment.getPath(), issueComment);
          IssueComment issueCommentPosted = cr.getEntity(IssueComment.class);
          issueComment.setId(issueCommentPosted.getId());
          issueCommentPosted.setIssue(issue);
          // update document comment
          comment.setJiraId(issueComment.getId());
        }
        issueComments.add(issueComment);
      }
      // save comments in item because IDs may have been added
      store.setFields(item, //
          of(FieldNames.COMMENTS, comments));
    }
  }

  private JiraUser persistUser(Comment comment) {
    JiraUser jiraUser = JiraUser.createFromComment(comment);
    ClientResponse cr = restAccess.get(jiraUser.getSelfPath());
    if (!isResponseOk(cr)) {
      ClientResponse postResponse = restAccess.post(jiraUser.getPath(), jiraUser);
      if (isResponseOk(postResponse)) {
        jiraUser = postResponse.getEntity(JiraUser.class);
      }
    }
    return jiraUser;
  }

  void storeReference(Issue jiraIssue, ODocument workItem) {
    store.setFields(workItem, //
        of(FieldNames.JIRA_KEY_LINK, jiraIssue.getKey()), //
        of(FieldNames.JIRA_ID_LINK, jiraIssue.getId()));
  }

  void storeTimestampOfLastExport(ODocument workItem) {
    store.setFields(
        workItem, //
        of(FieldNames.JIRA_EXPORT_TIMESTAMP,
            StorageQuery.getField(workItem, FieldNames.MODIFIED, Date.from(Instant.now()))));
  }


  private Optional<Project> getProject() {
    Project projectConfig = new Project();
    projectConfig.setKey(settings.getJiraProjectKey());
    return Optional.ofNullable(restAccess.get(projectConfig.getSelfPath(), Project.class));
  }

  Issue createIssueInJira(Issue issue) {
    ClientResponse postResponse = restAccess.post(issue.getPath(), issue);
    if (postResponse.getStatus() == Status.CREATED.getStatusCode()) {
      return postResponse.getEntity(Issue.class);
    } else {
      System.err.println("Problems while creating issue: " + postResponse.getEntity(String.class));
      return null;
    }
  }

  private boolean updateIssueInJira(Issue issue) {
    ClientResponse postResponse = restAccess.put("/issue/" + issue.getKey(), issue);
    if (isResponseOk(postResponse)) {
      return true;
    } else {
      System.err.println("Problems while updating issue: " + postResponse.getEntity(String.class));
      return false;
    }
  }

  boolean forceUpdate() {
    return Settings.getInstance().isForceUpdate();
  }

  Issue createIssueFromWorkItem(ODocument workItem, Project project) {
    Issue issue = new Issue();
    IssueFields issueFields = issue.getFields();
    issueFields.setProject(project);

    for (Entry<String, Object> entry : workItem) {
      mappingRegistry.map(entry, issue, store);

      String field = entry.getKey();
      switch (field) {
        case WORK_ITEM_TYPE:
          String workitemType = (String) entry.getValue();
          switch (workitemType) {
            case TASK:
              issueFields.setIssuetype(getIssueType("Task", project));
              break;
            case STORY:
              issueFields.setIssuetype(getIssueType("User Story", project));
              break;
            case EPIC:
              issueFields.setIssuetype(getIssueType("Epic", project));
              break;
            case BUSINESSNEED:
              issueFields.setIssuetype(getIssueType("Business Need", project));
              break;
            case DEFECT:
              issueFields.setIssuetype(getIssueType("Bug", project));
              break;
            default:
              LOGGER.warning("Cannot determine issuetype for unknown workitemType: " + workitemType);
              break;
          }
          break;
        default:
          break;
      }
    }
    issue.setKey(settings.getJiraProjectKey() + '-' + workItem.field(FieldNames.ID));
    return issue;
  }

  private IssueType getIssueType(String issuetypeName, Project project) {
    String projectKey = project.getKey();
    if (existingIssueTypes == null) {
      IssueMetadata issueMetadata =
          restAccess.get("/issue/createmeta/?expand=projects.issuetypes.fields.", IssueMetadata.class);
      existingIssueTypes = new HashMap<>();
      existingIssueTypes.put(projectKey, issueMetadata.getProject(projectKey).get().getIssuetypes());
    }

    List<IssueType> issuesTypesByProject = existingIssueTypes.get(projectKey);

    Supplier<IssueType> createIssueTypeForName = () -> {
      return createIssueType(issuetypeName);
    };

    IssueType issueType = getIssueTypeByName(issuetypeName, issuesTypesByProject).orElseGet(createIssueTypeForName);

    if (!issuesTypesByProject.contains(issueType)) {
      issuesTypesByProject.add(issueType);
    }
    return issueType;
  }

  private IssueType createIssueType(String issuetypeName) {
    IssueType newIssueType = new IssueType();
    newIssueType.setName(issuetypeName);
    newIssueType = restAccess.post(newIssueType.getPath(), newIssueType, IssueType.class);
    return newIssueType;
  }

  private Optional<IssueType> getIssueTypeByName(String name, Collection<IssueType> types) {
    List<IssueType> filteredTypes =
        types.stream().filter(issuetype -> issuetype.getName().equals(name)).collect(Collectors.toList());
    if (filteredTypes.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(filteredTypes.get(0));
    }

  }

  private boolean isResponseOk(ClientResponse cr) {
    return cr.getStatus() >= Status.OK.getStatusCode() && cr.getStatus() <= Status.PARTIAL_CONTENT.getStatusCode();
  }
}
