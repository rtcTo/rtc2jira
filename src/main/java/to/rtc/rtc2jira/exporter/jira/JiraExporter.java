package to.rtc.rtc2jira.exporter.jira;

import java.util.Base64;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.storage.StorageEngine;

public class JiraExporter implements Exporter {

  private StorageEngine engine;
  private Settings settings;
  private Client client;
  private String jiraRestHome;
  private String authentification;

  @Override
  public void initialize(Settings settings, StorageEngine engine) {
    this.settings = settings;
    this.engine = engine;
    this.client = Client.create();
  }

  @Override
  public boolean isConfigured() {
    boolean isConfigured = false;
    if (settings.hasJiraProperties()) {
      String userAndPassword = settings.getJiraUser() + ':' + settings.getJiraPassword();
      authentification = new String(Base64.getEncoder().encode(userAndPassword.getBytes()));
      jiraRestHome = settings.getJiraUrl() + "/rest/api/2";
      WebResource webResource = client.resource(jiraRestHome + "/project");
      ClientResponse response = webResource.header("Authorization", "Basic " + authentification)
          .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
          .get(ClientResponse.class);
      isConfigured = response.getStatusInfo().getStatusCode() == Status.OK.getStatusCode();
    }
    return isConfigured;
  }

  @Override
  public void export() throws Exception {
    WebResource sampleProjectResource = client.resource(jiraRestHome + "/project/10001");
    ClientResponse response = sampleProjectResource
        .header("Authorization", "Basic " + authentification).type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

    Project project = response.getEntity(Project.class);

  }

}
