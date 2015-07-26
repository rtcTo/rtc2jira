package to.rtc.rtc2jira.exporter.jira;

import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.exporter.jira.entities.Project;
import to.rtc.rtc2jira.exporter.jira.entities.ProjectOverview;
import to.rtc.rtc2jira.storage.StorageEngine;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.GenericType;

public class JiraExporter implements Exporter {

  private StorageEngine engine;
  private Settings settings;
  private JiraRestAccess restAccess;

  @Override
  public void initialize(Settings settings, StorageEngine engine) {
    this.settings = settings;
    this.engine = engine;
  }

  @Override
  public boolean isConfigured() {
    boolean isConfigured = false;
    if (settings.hasJiraProperties()) {
      restAccess =
          new JiraRestAccess(settings.getJiraUrl(), settings.getJiraUser(),
              settings.getJiraPassword());
      ClientResponse response = restAccess.getResponse("/project");
      isConfigured = response.getStatus() == Status.OK.getStatusCode();
    }
    return isConfigured;
  }

  @Override
  public void export() throws Exception {
    List<ProjectOverview> projects =
        restAccess.get("/project", new GenericType<List<ProjectOverview>>() {});
    Project project = restAccess.get("/project/10001", Project.class);

    JSONObject data = createIssueData(project);
    String response = restAccess.post("/issue/", data.toString(), String.class);
  }

  private JSONObject createIssueData(Project project) throws JSONException {
    JSONObject data = new JSONObject();
    JSONObject fields = new JSONObject();
    fields.put("summary", "Test REST");
    fields.put("description",
        "Creating of an issue using project keys and issue type names using the REST API");
    fields.put("project", new JSONObject().put("id", project.getId()));
    fields.put("issuetype", new JSONObject().put("name", "Task"));

    data.put("fields", fields);
    return data;
  }
}
