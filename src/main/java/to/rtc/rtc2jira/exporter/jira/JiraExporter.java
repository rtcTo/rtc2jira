package to.rtc.rtc2jira.exporter.jira;

import static to.rtc.rtc2jira.storage.Field.of;
import static to.rtc.rtc2jira.storage.WorkItemConstants.DESCRIPTION;
import static to.rtc.rtc2jira.storage.WorkItemConstants.ID;
import static to.rtc.rtc2jira.storage.WorkItemConstants.SUMMARY;
import static to.rtc.rtc2jira.storage.WorkItemConstants.WORK_ITEM_TYPE;
import static to.rtc.rtc2jira.storage.WorkItemFieldNames.JIRA_ID_LINK;
import static to.rtc.rtc2jira.storage.WorkItemFieldNames.JIRA_KEY_LINK;
import static to.rtc.rtc2jira.storage.WorkItemTypes.BUSINESSNEED;
import static to.rtc.rtc2jira.storage.WorkItemTypes.EPIC;
import static to.rtc.rtc2jira.storage.WorkItemTypes.STORY;
import static to.rtc.rtc2jira.storage.WorkItemTypes.TASK;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueFields;
import to.rtc.rtc2jira.exporter.jira.entities.IssueMetadata;
import to.rtc.rtc2jira.exporter.jira.entities.IssueType;
import to.rtc.rtc2jira.exporter.jira.entities.Project;
import to.rtc.rtc2jira.storage.StorageEngine;
import to.rtc.rtc2jira.storage.StorageQuery;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;

public class JiraExporter implements Exporter {

  private StorageEngine store;
  private Settings settings;
  private JiraRestAccess restAccess;
  private Map<String, List<IssueType>> existingIssueTypes;

  @Override
  public void initialize(Settings settings, StorageEngine store) {
    this.settings = settings;
    this.store = store;
  }

  @Override
  public boolean isConfigured() {
    boolean isConfigured = false;
    if (settings.hasJiraProperties()) {
      restAccess = new JiraRestAccess(settings.getJiraUrl(), settings.getJiraUser(), settings.getJiraPassword());
      ClientResponse response = restAccess.get("/myself");
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
      for (ODocument workItem : StorageQuery.getRTCWorkItems(store)) {
        Issue issue = createIssueFromWorkItem(workItem, projectOptional.get());
        Issue jiraIssue = createIssueInJira(issue);
        storeReference(Optional.ofNullable(jiraIssue), workItem);
      }
    }
  }

  private void storeReference(Optional<Issue> optionalJiraIssue, ODocument workItem) {
    optionalJiraIssue.ifPresent(jiraIssue -> {
      store.setFields(workItem, //
          of(JIRA_KEY_LINK, jiraIssue.getKey()), //
          of(JIRA_ID_LINK, jiraIssue.getId()));
    });
  }

  private Optional<Project> getProject() {
    return Optional.ofNullable(restAccess.get("/project/" + settings.getJiraProjectKey(), Project.class));
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

  private Issue createIssueFromWorkItem(ODocument workItem, Project project) throws Exception {
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
            default:
              System.out.println("Cannot determine issuetype for unknown workitemType: " + workitemType);
              break;
          }
          break;
        default:
          break;
      }
    }
    issueFields.setSummary(issue.getId() + ": " + issueFields.getSummary());
    issue.setId(StorageQuery.getField(workItem, JIRA_ID_LINK, ""));
    issue.setKey(StorageQuery.getField(workItem, JIRA_KEY_LINK, ""));
    return issue;
  }

  private IssueType getIssueType(String issuetypeName, Project project) throws Exception {
    String projectKey = project.getKey();
    if (existingIssueTypes == null) {
      IssueMetadata issueMetadata =
          restAccess.get("/issue/createmeta/?expand=projects.issuetypes.fields.", IssueMetadata.class);
      existingIssueTypes = new HashMap<>();
      existingIssueTypes.put(projectKey, issueMetadata.getProject(projectKey).get().getIssuetypes());
    }

    // boolean isNotAssigned = false;
    // for (List<IssueType> issueTypes : existingIssueTypes.values()) {
    // if (getIssueTypeByName(issuetypeName, issueTypes).isPresent()) {
    // isNotAssigned = true;
    // break;
    // }
    // }
    // if (isNotAssigned) {
    // System.out.println("Please assign issueType " + issuetypeName + " to Project " +
    // project.getName());
    // // throw exception?
    // }
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
