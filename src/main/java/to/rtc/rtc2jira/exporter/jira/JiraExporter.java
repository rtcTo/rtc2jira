package to.rtc.rtc2jira.exporter.jira;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.storage.StorageEngine;

public class JiraExporter implements Exporter {

  private StorageEngine engine;
  private Settings settings;

  @Override
  public void initialize(Settings settings, StorageEngine engine) {
    this.settings = settings;
    this.engine = engine;
  }

  @Override
  public boolean isConfigured() {
    boolean isConfigured = false;
    return isConfigured;
  }

  @Override
  public void export() throws Exception {}

}
