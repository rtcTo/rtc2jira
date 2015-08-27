package to.rtc.rtc2jira.exporter.jira;

import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.jira.entities.Project;
import to.rtc.rtc2jira.storage.StorageEngine;

public class JiraPersistence {

  private static final Logger LOGGER = Logger.getLogger(JiraPersistence.class.getName());

  private StorageEngine store;
  private Settings settings;
  private JiraRestAccess restAccess;

  private Project project;

  static private JiraPersistence SINGLETON;


  private JiraPersistence(StorageEngine store) {
    this.store = store;
    this.settings = Settings.getInstance();
    restAccess =
        new JiraRestAccess(settings.getJiraUrl(), settings.getJiraUser(),
            settings.getJiraPassword());
  }

  public static JiraPersistence getInstance() {
    if (SINGLETON == null) {
      try {
        SINGLETON = new JiraPersistence(new StorageEngine());
      } catch (Exception e) {
        LOGGER.log(
            Level.SEVERE,
            "A problem occurred while creating the storage engine. Caused by message: "
                + e.getLocalizedMessage());
        throw new RuntimeException(e);
      }
    }
    return JiraPersistence.SINGLETON;
  }

  public Project getProject() {
    if (this.project == null) {
      this.project = restAccess.get("/project/" + settings.getJiraProjectKey(), Project.class);
    }
    return this.project;
  }

  public StorageEngine getStore() {
    return store;
  }

  public JiraRestAccess getRestAccess() {
    return restAccess;
  }


}
