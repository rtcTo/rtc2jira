package to.rtc.rtc2jira.exporter.jira;

import static to.rtc.rtc2jira.storage.Field.of;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;
import to.rtc.rtc2jira.storage.StorageQuery;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;

public class JiraExporter implements Exporter {


  @Override
  public void initialize(Settings settings, StorageEngine engine) throws Exception {}

  @Override
  public boolean isConfigured() {
    boolean isConfigured = false;

    ClientResponse response = JiraPersistence.getInstance().getRestAccess().get("/myself");
    if (response.getStatus() == Status.OK.getStatusCode()) {
      isConfigured = true;
    } else {
      System.err.println("Unable to connect to jira repository: " + response.toString());
    }
    return isConfigured;
  }

  private boolean forceCreate() {
    return Settings.getInstance().isForceCreate();
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

  void createItem(ODocument item) {
    Optional<Issue> optionalIssue = Issue.createFromWorkItem(item).save();
    optionalIssue.ifPresent(issue -> {
      storeReference(issue, item);
      storeTimestampOfLastExport(item);
    });
  }

  private void updateItem(ODocument item) {
    Optional<Issue> optionalIssue = Issue.createFromWorkItem(item).save();
    optionalIssue.ifPresent(issue -> {
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


  // Issue createIssueInJira(Issue issue) {
  // ClientResponse postResponse =
  // JiraPersistence.getInstance().getRestAccess().post("/issue", new IssueDto(issue));
  // if (postResponse.getStatus() == Status.CREATED.getStatusCode()) {
  // return postResponse.getEntity(Issue.class);
  // } else {
  // System.err.println("Problems while creating issue: " + postResponse.getEntity(String.class));
  // return null;
  // }
  // }
  //
  // private boolean updateIssueInJira(Issue issue) {
  // ClientResponse postResponse =
  // JiraPersistence.getInstance().getRestAccess()
  // .put("/issue/" + issue.getKey(), new IssueDto(issue));
  // if (postResponse.getStatus() >= Status.OK.getStatusCode()
  // && postResponse.getStatus() <= Status.PARTIAL_CONTENT.getStatusCode()) {
  // return true;
  // } else {
  // System.err.println("Problems while updating issue: " + postResponse.getEntity(String.class));
  // return false;
  // }
  // }


}
