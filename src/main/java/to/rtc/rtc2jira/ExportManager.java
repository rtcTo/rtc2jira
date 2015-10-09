package to.rtc.rtc2jira;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;
import to.rtc.rtc2jira.storage.StorageQuery;

import com.orientechnologies.orient.core.record.impl.ODocument;

public class ExportManager {
  private static Logger LOGGER = Logger.getLogger(ExportManager.class.getName());

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
          LOGGER.info("Exported workitem " + workItem.field(FieldNames.ID));
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
    }
    return exporters;
  }

}
