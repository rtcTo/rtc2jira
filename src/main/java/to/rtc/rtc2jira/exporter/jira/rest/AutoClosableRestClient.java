package to.rtc.rtc2jira.exporter.jira.rest;

import java.io.IOException;

import com.atlassian.jira.rest.client.api.AuditRestClient;
import com.atlassian.jira.rest.client.api.ComponentRestClient;
import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.MetadataRestClient;
import com.atlassian.jira.rest.client.api.MyPermissionsRestClient;
import com.atlassian.jira.rest.client.api.ProjectRestClient;
import com.atlassian.jira.rest.client.api.ProjectRolesRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.SessionRestClient;
import com.atlassian.jira.rest.client.api.UserRestClient;
import com.atlassian.jira.rest.client.api.VersionRestClient;

/**
 * Wraps an {@link JiraRestClient} with an {@link AutoCloseable}
 * 
 * @author Manuel
 */
public class AutoClosableRestClient implements AutoCloseable, JiraRestClient {

  private JiraRestClient client;

  AutoClosableRestClient(JiraRestClient client) {
    this.client = client;
  }

  @Override
  public void close() throws IOException {
    client.close();
  }

  @Override
  public IssueRestClient getIssueClient() {
    return client.getIssueClient();
  }

  @Override
  public SessionRestClient getSessionClient() {
    return client.getSessionClient();
  }

  @Override
  public UserRestClient getUserClient() {
    return client.getUserClient();
  }

  @Override
  public ProjectRestClient getProjectClient() {
    return client.getProjectClient();
  }

  @Override
  public ComponentRestClient getComponentClient() {
    return client.getComponentClient();
  }

  @Override
  public MetadataRestClient getMetadataClient() {
    return client.getMetadataClient();
  }

  @Override
  public SearchRestClient getSearchClient() {
    return client.getSearchClient();
  }

  @Override
  public VersionRestClient getVersionRestClient() {
    return client.getVersionRestClient();
  }

  @Override
  public ProjectRolesRestClient getProjectRolesRestClient() {
    return client.getProjectRolesRestClient();
  }

  @Override
  public AuditRestClient getAuditRestClient() {
    return client.getAuditRestClient();
  }

  @Override
  public MyPermissionsRestClient getMyPermissionsRestClient() {
    return client.getMyPermissionsRestClient();
  }
}
