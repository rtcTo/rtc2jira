package to.rtc.rtc2jira;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.exporter.github.GitHubExporter;
import to.rtc.rtc2jira.exporter.jira.JiraExporter;
import to.rtc.rtc2jira.exporter.systemout.LoggingExporter;
import to.rtc.rtc2jira.storage.StorageEngine;
import to.rtc.rtc2jira.storage.StorageQuery;

import com.orientechnologies.orient.core.record.impl.ODocument;

public class ExportManager {

  private List<Exporter> exporters;

  public ExportManager() {
    exporters = new ArrayList<Exporter>();
  }

  public void export(Settings settings, StorageEngine storageEngine) throws Exception {
    for (Exporter exporter : getExporters()) {
      if (exporter.isConfigured()) {
        exporter.initialize(settings, storageEngine);
        for (ODocument workItem : StorageQuery.getRTCWorkItems(storageEngine)) {
          exporter.createOrUpdateItem(workItem);
        }
      }
    }
  }


  public void addExporters(Exporter... exporters) {
    this.exporters.addAll(Arrays.asList(exporters));
  }

  List<Exporter> getExporters() {
    if (exporters == null) {
      exporters = new ArrayList<>();
      exporters.add(new GitHubExporter());
      exporters.add(new JiraExporter());
      exporters.add(new LoggingExporter());
    }
    return exporters;
  }

}
