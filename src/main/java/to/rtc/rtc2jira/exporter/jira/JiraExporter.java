package to.rtc.rtc2jira.exporter.jira;

import static to.rtc.rtc2jira.storage.WorkItemConstants.DESCRIPTION;
import static to.rtc.rtc2jira.storage.WorkItemConstants.ID;
import static to.rtc.rtc2jira.storage.WorkItemConstants.SUMMARY;
import static to.rtc.rtc2jira.storage.WorkItemConstants.WORK_ITEM_TYPE;
import static to.rtc.rtc2jira.storage.WorkItemTypes.BUSINESSNEED;
import static to.rtc.rtc2jira.storage.WorkItemTypes.EPIC;
import static to.rtc.rtc2jira.storage.WorkItemTypes.STORY;
import static to.rtc.rtc2jira.storage.WorkItemTypes.TASK;

import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.GenericType;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueFields;
import to.rtc.rtc2jira.exporter.jira.entities.IssueType;
import to.rtc.rtc2jira.exporter.jira.entities.Project;
import to.rtc.rtc2jira.storage.StorageEngine;

public class JiraExporter implements Exporter {

  private StorageEngine store;
  private Settings settings;
  private JiraRestAccess restAccess;
  private List<IssueType> _existingIssueTypes;

  @Override
  public void initialize(Settings settings, StorageEngine store) {
    this.settings = settings;
    this.store = store;
  }

  @Override
  public boolean isConfigured() {
    boolean isConfigured = false;
    if (settings.hasJiraProperties()) {
      restAccess = new JiraRestAccess(settings.getJiraUrl(), settings.getJiraUser(),
          settings.getJiraPassword());
      ClientResponse response = restAccess.get("/project");
      if (response.getStatus() == Status.OK.getStatusCode()) {
        isConfigured = true;
      } else {
        System.err.println("Unable to connect to jira repository: " + response.toString());
      }
    }
    return isConfigured;
  }

  @Override
  public void export() throws Exception {
    Optional<Project> projectOptional = getProject();
    if (projectOptional.isPresent()) {
      for (ODocument workItem : store.getRTCWorkItems()) {
        Issue issue = createIssueFromWorkItem(workItem, projectOptional.get());
        Issue jiraIssue = createIssueInJira(issue);
        storeReference(jiraIssue);
      }
    }
  }

  private void storeReference(Issue jiraIssue) {
    // TODO Store key/id in o2 database
  }

  private Optional<Project> getProject() {
    List<Project> projects =
        restAccess.get("/project", new GenericType<List<Project>>() {});
    for (Project project : projects) {
      if (project.getKey().equals(settings.getJiraProjectKey())) {
        return Optional.of(project);
      }
    }
    return Optional.ofNullable(null);
  }

  private Issue createIssueInJira(Issue issue) throws Exception {
    ClientResponse postResponse = restAccess.post("/issue", issue);
    if (postResponse.getStatus() == Status.CREATED.getStatusCode()) {
      return postResponse.getEntity(Issue.class);
    } else {
      System.err.println("Problems while creating issue: " + postResponse.getEntity(String.class));
      return null;
    }
  }

  private Issue createIssueFromWorkItem(ODocument workItem, Project project)
      throws Exception {
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
          issueFields.setDescription(htmlText);
          break;
        case WORK_ITEM_TYPE:
          String workitemType = (String) entry.getValue();
          switch (workitemType) {
            case TASK:
              issueFields.setIssuetype(getIssueType("Task"));
              break;
            case STORY:
              issueFields.setIssuetype(getIssueType("User Story"));
              break;
            case EPIC:
              issueFields.setIssuetype(getIssueType("Epic"));
              break;
            case BUSINESSNEED:
              issueFields.setIssuetype(getIssueType("Business Need"));
              break;
            default:
              System.out
                  .println("Cannot determine issuetype for unknown workitemType: " + workitemType);
              break;
          }
          break;
        default:
          break;
      }
    }
    return issue;
  }

  private IssueType getIssueType(String issuetypeName) throws Exception {
    if (_existingIssueTypes == null) {
      _existingIssueTypes = restAccess.get("/issuetype", new GenericType<List<IssueType>>() {});
    }
    for (IssueType issuetype : _existingIssueTypes) {
      if (issuetype.getName().equals(issuetypeName)) {
        return issuetype;
      }
    }

    IssueType newIssueType = new IssueType();
    newIssueType.setName(issuetypeName);
    IssueType createdIssueType = restAccess.post("/issuetype", newIssueType, IssueType.class);
    _existingIssueTypes.add(createdIssueType);
    return newIssueType;

  }
}
