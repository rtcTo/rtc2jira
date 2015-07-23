package to.rtc.rtc2jira;

import java.util.ArrayList;
import java.util.List;

import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.exporter.GitHubExporter;
import to.rtc.rtc2jira.exporter.JiraExporter;
import to.rtc.rtc2jira.extract.RTCExtractor;
import to.rtc.rtc2jira.mapping.DefaultMappingRegistry;
import to.rtc.rtc2jira.mapping.DirectMapping;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author roman.schaller
 *
 */
public class Main {

  public static void main(String[] args) throws Exception {
    registerDefaultMappings();
    Settings settings = Settings.getInstance();
    try (StorageEngine storageEngine = new StorageEngine()) {
      RTCExtractor extractor = new RTCExtractor(settings, storageEngine);
      extractor.extract();
      for (Exporter exporter : getExporters()) {
        exporter.initialize(settings, storageEngine);
        if (exporter.isConfigured()) {
          exporter.export();
        }
      }
    }
  }

  private static void registerDefaultMappings() {
    DefaultMappingRegistry registry = DefaultMappingRegistry.getInstance();
    registry.register("summary", new DirectMapping("summary"));
    registry.register("description", new DirectMapping("description"));
    registry.register("workItemType", new DirectMapping("workItemType"));
  }

  private static List<Exporter> getExporters() {
    List<Exporter> exporters = new ArrayList<>();
    exporters.add(new GitHubExporter());
    exporters.add(new JiraExporter());
    return exporters;
  }
}
