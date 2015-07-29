package to.rtc.rtc2jira.exporter.jira;

import java.io.IOException;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.exporter.jira.rest.AutoClosableRestClient;
import to.rtc.rtc2jira.exporter.jira.rest.RestClientFactory;
import to.rtc.rtc2jira.storage.StorageEngine;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Attachment;
import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.util.concurrent.Promise;

public class JiraExporter implements Exporter {

  private StorageEngine engine;
  private Settings settings;
  private AutoClosableRestClient restAccess;

  @Override
  public void initialize(Settings settings, StorageEngine engine) {
    this.settings = settings;
    this.engine = engine;
  }

  @Override
  public boolean isConfigured() {
    boolean isConfigured = false;
    if (settings.hasJiraProperties()) {
      try (JiraRestClient client = RestClientFactory.create(settings)) {
        client.getSessionClient().getCurrentSession();
      } catch (RestClientException | IOException e) {
        System.err.println("Problem while connecting to session: " + e);
        isConfigured = false;
      }
    }
    return isConfigured;
  }

  @Override
  public void export() throws Exception {
    try (JiraRestClient client = RestClientFactory.create(settings)) {
      IssueRestClient issueClient = client.getIssueClient();
      Promise<Issue> issuePromise = issueClient.getIssue("WOR-2");
      Issue issue = issuePromise.get();
      Iterable<Attachment> attachments = issue.getAttachments();
      Iterable<BasicProject> projectsIterable = client.getProjectClient().getAllProjects().get();
    }
  }

}
