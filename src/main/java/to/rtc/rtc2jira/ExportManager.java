package to.rtc.rtc2jira;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;
import to.rtc.rtc2jira.storage.StorageQuery;

import com.orientechnologies.orient.core.record.impl.ODocument;

public class ExportManager {
  private static Logger LOGGER = Logger.getLogger(ExportManager.class.getName());

  public static final StreamHandler DEFAULT_LOG_HANDLER;

  static {
    FileHandler fh = null;
    try {
      LocalDateTime date = LocalDateTime.now();
      String timestamp =
          date.getYear() + "_" + date.getMonthValue() + "_" + date.getDayOfMonth() + "__" + date.getHour() + "_"
              + date.getMinute();
      fh = new FileHandler("C:/workspace/gitRepRtcToJira/rtc2jira/DefaultExportLog_" + timestamp + ".log");
      SimpleFormatter formatter = new SimpleFormatter();
      fh.setFormatter(formatter);
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    DEFAULT_LOG_HANDLER = fh;
    LOGGER.addHandler(DEFAULT_LOG_HANDLER);
  }

  private List<Exporter> exporters;

  public ExportManager() {
    exporters = new ArrayList<Exporter>();
  }

  public void export(Settings settings, StorageEngine storageEngine) throws Exception {
    for (Exporter exporter : getExporters()) {
      if (exporter.isConfigured()) {
        exporter.initialize(settings, storageEngine);
        Collection<Integer> rtcWorkItemRange = Settings.getInstance().getRtcWorkItemRange();
        if (rtcWorkItemRange != null) {
          rtcWorkItemRange = new HashSet<Integer>(rtcWorkItemRange);
        }
        for (ODocument workItem : StorageQuery.getRTCWorkItems(storageEngine)) {
          String workItemId = workItem.field(FieldNames.ID);
          workItem = StorageQuery.getRTCWorkItem(storageEngine, Integer.parseInt(workItemId));
          Integer idSeq = Integer.valueOf(workItemId);
          if (rtcWorkItemRange == null || rtcWorkItemRange.contains(idSeq)) {
            exporter.createOrUpdateItem(workItem);
            LOGGER.info("Exported workitem " + workItem.field(FieldNames.ID));
          }
        }
        exporter.postExport();
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
