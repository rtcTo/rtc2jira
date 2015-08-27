package to.rtc.rtc2jira.exporter.jira;

import static to.rtc.rtc2jira.storage.Field.of;
import static to.rtc.rtc2jira.storage.FieldNames.*;
import static to.rtc.rtc2jira.storage.WorkItemTypes.*;

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
import to.rtc.rtc2jira.exporter.jira.entities.IssueFields;
import to.rtc.rtc2jira.exporter.jira.entities.IssueMetadata;
import to.rtc.rtc2jira.exporter.jira.entities.IssueType;
import to.rtc.rtc2jira.exporter.jira.entities.Project;
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
  Optional<Project> projectOptional;

  @Override
  public boolean isConfigured() {
    return Settings.getInstance().hasJiraProperties();
  }

  @Override
  public void initialize(Settings settings, StorageEngine store) throws Exception {
    this.settings = settings;
    this.store = store;
    restAccess = new JiraRestAccess(settings.getJiraUrl(), settings.getJiraUser(), settings.getJiraPassword());
    this.projectOptional = getProject();
    ClientResponse response = restAccess.get("/myself");
    if (response.getStatus() == Status.OK.getStatusCode()) {
    } else {
      System.err.println("Unable to connect to jira repository: " + response.toString());
    }
  }


  private boolean forceCreate() {
    return settings.isForceCreate();
  }

  @Override
  public void createOrUpdateItem(ODocument item) throws Exception {
    String id = StorageQuery.getField(item, FieldNames.JIRA_ID_LINK, "");
    if (forceCreate() || id.isEmpty()) {
      createItem(item);
    } else {
      Date modified = StorageQuery.getField(item, FieldNames.MODIFIED, Date.from(Instant.now()));
      Date lastExport = StorageQuery.getField(item, FieldNames.JIRA_EXPORT_TIMESTAMP, new Date(0));
      if (modified.compareTo(lastExport) > 0) {
        updateItem(item);
      }
    }
  }

  void createItem(ODocument item) throws Exception {
    projectOptional.ifPresent(project -> {
      Issue issue = createIssueFromWorkItem(item, project);
      Issue jiraIssue = createIssueInJira(issue);
      if (jiraIssue != null) {
        storeReference(jiraIssue, item);
        storeTimestampOfLastExport(item);
      }
    });
  }

  private void updateItem(ODocument item) {
    projectOptional.ifPresent(project -> {
      Issue issue = createIssueFromWorkItem(item, project);
      boolean success = updateIssueInJira(issue);
      if (success) {
        storeTimestampOfLastExport(item);
      }
    });
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
    System.out.println("testing");
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


  Issue createIssueFromWorkItem(ODocument workItem, Project project) {
    Issue issue = new Issue();
    IssueFields issueFields = issue.getFields();
    issueFields.setProject(project);
    for (Entry<String, Object> entry : workItem) {
      String field = entry.getKey();
      switch (field) {
        case ID:
          String id = (String) entry.getValue();
          issue.setId(id);
          break;
        case SUMMARY:
          String summary = (String) entry.getValue();
          issueFields.setSummary(summary);
          break;
        case DESCRIPTION:
          String htmlText = (String) entry.getValue();
          // TODO: replace HTML style formatting with JIRA formatting
          issueFields.setDescription(htmlText);
          break;
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
    issueFields.setSummary(issue.getId() + ": " + issueFields.getSummary());
    issue.setId(StorageQuery.getField(workItem, FieldNames.JIRA_ID_LINK, ""));
    issue.setKey(StorageQuery.getField(workItem, FieldNames.JIRA_KEY_LINK, ""));
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
