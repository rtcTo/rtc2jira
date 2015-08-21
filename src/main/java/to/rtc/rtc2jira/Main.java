package to.rtc.rtc2jira;

import to.rtc.rtc2jira.exporter.github.GitHubExporter;
import to.rtc.rtc2jira.exporter.jira.JiraExporter;
import to.rtc.rtc2jira.exporter.systemout.LoggingExporter;
import to.rtc.rtc2jira.importer.RTCImporter;
import to.rtc.rtc2jira.storage.StorageEngine;


/**
 * @author roman.schaller
 *
 */
public class Main {

  public static void main(String[] args) throws Exception {
    Settings settings = Settings.getInstance();
    setUpProxy(settings);
    ExportManager exportManager = new ExportManager();
    exportManager.addExporters(new GitHubExporter(), new JiraExporter(), new LoggingExporter());
    try (StorageEngine storageEngine = new StorageEngine()) {
      doImport(settings, storageEngine);
      exportManager.export(settings, storageEngine);
    }
  }

  private static void doImport(Settings settings, StorageEngine storageEngine) {
    if (settings.hasRtcProperties()) {
      new RTCImporter(settings, storageEngine).doImport();
    }
  }

  private static void setUpProxy(Settings settings) {
    if (settings.hasProxySettings()) {
      System.setProperty("http.proxyHost", settings.getProxyHost());
      System.setProperty("http.proxyPort", settings.getProxyPort());
      System.setProperty("https.proxyHost", settings.getProxyHost());
      System.setProperty("https.proxyPort", settings.getProxyPort());
    }
  }

}
