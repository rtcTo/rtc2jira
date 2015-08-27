package to.rtc.rtc2jira.exporter.jira;

import static to.rtc.rtc2jira.storage.Field.of;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.exporter.jira.entities.DummyIssue;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.Project;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;
import to.rtc.rtc2jira.storage.StorageQuery;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;

public class JiraExporter implements Exporter {
  private Settings settings;
  private JiraRestAccess restAccess;
  Optional<Project> projectOptional;
  private int highestExistingId = -1;

  @Override
  public boolean isConfigured() {
    return Settings.getInstance().hasJiraProperties();
  }

  @Override
  public void initialize(Settings settings, StorageEngine store) throws Exception {
    this.settings = settings;
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
    if (modified.compareTo(lastExport) > 0) {
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
      DummyIssue dummyIssue = new DummyIssue();
      dummyIssue.getFields().setProject(project);
      dummyIssue.getFields().setIssuetype(Issue.getIssueType("Task"));
      dummyIssue.getFields().setSummary("Dummy");
      dummyIssue.getFields().setDescription(
          "This is just a dummy issue. Delete it after successfully migrating to Jira.");
      Issue createdIssue = createIssueInJira(dummyIssue);
      String highestAsString = createdIssue.getKey().replace(settings.getJiraProjectKey() + '-', "");
      highestExistingId = Integer.parseInt(highestAsString);
    }
  }

  private void updateItem(ODocument item) {
    Optional<Issue> optionalIssue = Issue.createFromWorkItem(item).save();
    optionalIssue.ifPresent(issue -> {
      storeReference(issue, item);
      storeTimestampOfLastExport(item);
    });
  }

  void storeReference(Issue jiraIssue, ODocument workItem) {
    JiraPersistence.getInstance().getStore().setFields(workItem, //
        of(FieldNames.JIRA_KEY_LINK, jiraIssue.getKey()), //
        of(FieldNames.JIRA_ID_LINK, jiraIssue.getId()));
  }

  void storeTimestampOfLastExport(ODocument workItem) {
    JiraPersistence
        .getInstance()
        .getStore()
        .setFields(
            workItem, //
            of(FieldNames.JIRA_EXPORT_TIMESTAMP,
                StorageQuery.getField(workItem, FieldNames.MODIFIED, Date.from(Instant.now()))));
  }

  private Optional<Project> getProject() {
    return Optional.ofNullable(restAccess.get("/project/" + settings.getJiraProjectKey(), Project.class));
  }

  Issue createIssueInJira(DummyIssue issue) {
    ClientResponse postResponse = restAccess.post("/issue", issue);
    if (postResponse.getStatus() == Status.CREATED.getStatusCode()) {
      return postResponse.getEntity(Issue.class);
    } else {
      System.err.println("Problems while creating issue: " + postResponse.getEntity(String.class));
      return null;
    }
  }
}
