package to.rtc.rtc2jira.exporter.jira.rest;

import java.net.URI;

import to.rtc.rtc2jira.Settings;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

public abstract class RestClientFactory {


  public static JiraRestClient create(String urlAsString, String user, String password) {
    JiraRestClient restClient =
        new AsynchronousJiraRestClientFactory().create(URI.create(urlAsString),
            new BasicHttpAuthenticationHandler(user, password));
    return new AutoClosableRestClient(restClient);
  }

  public static JiraRestClient create(Settings settings) {
    return create(settings.getJiraUrl(), settings.getJiraUser(), settings.getJiraPassword());
  }
}
