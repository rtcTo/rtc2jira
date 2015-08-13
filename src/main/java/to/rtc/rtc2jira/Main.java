package to.rtc.rtc2jira;

import java.util.ArrayList;
import java.util.List;

import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.exporter.github.GitHubExporter;
import to.rtc.rtc2jira.exporter.jira.JiraExporter;
import to.rtc.rtc2jira.exporter.systemout.SystemOutExporter;
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
    try (StorageEngine storageEngine = new StorageEngine()) {
      doImport(settings, storageEngine);
      export(settings, storageEngine);
    }
  }

  private static void export(Settings settings, StorageEngine storageEngine) throws Exception {
    for (Exporter exporter : getExporters()) {
      exporter.initialize(settings, storageEngine);
      if (exporter.isConfigured()) {
        exporter.export();
      }
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

  private static List<Exporter> getExporters() {
    List<Exporter> exporters = new ArrayList<>();
    exporters.add(new GitHubExporter());
    exporters.add(new JiraExporter());
    exporters.add(new SystemOutExporter());
    return exporters;
  }
}
