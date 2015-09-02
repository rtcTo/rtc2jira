package to.rtc.rtc2jira.exporter.jira;

import static to.rtc.rtc2jira.storage.Field.of;
import static to.rtc.rtc2jira.storage.FieldNames.WORK_ITEM_TYPE;
import static to.rtc.rtc2jira.storage.WorkItemTypes.BUSINESSNEED;
import static to.rtc.rtc2jira.storage.WorkItemTypes.DEFECT;
import static to.rtc.rtc2jira.storage.WorkItemTypes.EPIC;
import static to.rtc.rtc2jira.storage.WorkItemTypes.STORY;
import static to.rtc.rtc2jira.storage.WorkItemTypes.TASK;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueComment;
import to.rtc.rtc2jira.exporter.jira.entities.IssueFields;
import to.rtc.rtc2jira.exporter.jira.entities.IssueMetadata;
import to.rtc.rtc2jira.exporter.jira.entities.IssueType;
import to.rtc.rtc2jira.exporter.jira.entities.Project;
import to.rtc.rtc2jira.exporter.jira.mapping.MappingRegistry;
import to.rtc.rtc2jira.storage.Comment;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;
import to.rtc.rtc2jira.storage.StorageQuery;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;

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

  private void updateItem(ODocument item) {
    projectOptional.ifPresent(project -> {
      Issue issue = createIssueFromWorkItem(item, project);
      persistIssue(item, issue);
      persistNewComments(item, issue);
      persistAttachments(item, issue);
    });
  }

  private void persistIssue(ODocument item, Issue issue) {
    boolean success = updateIssueInJira(issue);
    if (success) {
      storeReference(issue, item);
      storeTimestampOfLastExport(item);
    }
  }

  private void persistAttachments(ODocument item, Issue issue) {}


  private void persistNewComments(ODocument item, Issue issue) {
    List<IssueComment> issueComments = issue.getFields().getComment().getComments();
    List<Comment> comments = item.field(FieldNames.COMMENTS);
    if (comments != null) {
      for (Comment comment : comments) {
        IssueComment issueComment = IssueComment.createWithIdAndBody(issue, comment.getJiraId(), comment.getComment());
        if (comment.getJiraId() == null) {
          ClientResponse cr = restAccess.post(issueComment.getPath(), issueComment);
          IssueComment issueCommentResponse = cr.getEntity(IssueComment.class);
          issueComment.setId(issueCommentResponse.getId());
          comment.setJiraId(issueComment.getId());
        }
        issueComments.add(issueComment);
      }
      // save comments in item because IDs may have been added
      store.setFields(item, //
          of(FieldNames.COMMENTS, comments));
    }
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
    return Optional.ofNullable(restAccess.get("/project/" + settings.getJiraProjectKey(), Project.class));
  }

  Issue createIssueInJira(Issue issue) {
    ClientResponse postResponse = restAccess.post("/issue", issue);
    if (postResponse.getStatus() == Status.CREATED.getStatusCode()) {
      return postResponse.getEntity(Issue.class);
    } else {
      System.err.println("Problems while creating issue: " + postResponse.getEntity(String.class));
      return null;
    }
  }

  private boolean updateIssueInJira(Issue issue) {
    ClientResponse postResponse = restAccess.put("/issue/" + issue.getKey(), issue);
    if (postResponse.getStatus() >= Status.OK.getStatusCode()
        && postResponse.getStatus() <= Status.PARTIAL_CONTENT.getStatusCode()) {
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
    IssueType issueType =
        getIssueTypeByName(issuetypeName, issuesTypesByProject).orElse(createIssueType(issuetypeName));

    if (!issuesTypesByProject.contains(issueType)) {
      issuesTypesByProject.add(issueType);
    }
    return issueType;
  }

  private IssueType createIssueType(String issuetypeName) {
    IssueType newIssueType = new IssueType();
    newIssueType.setName(issuetypeName);
    newIssueType = restAccess.post("/issuetype", newIssueType, IssueType.class);
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

}
