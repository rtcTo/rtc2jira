/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.systemout;

import java.util.Map.Entry;
import java.util.logging.Logger;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;
import to.rtc.rtc2jira.storage.StorageQuery;

import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * @author roman.schaller
 *
 */
public class LoggingExporter implements Exporter {
  private static final Logger LOGGER = Logger.getLogger(LoggingExporter.class.getName());

  private StorageEngine engine;
  private Settings settings;

  @Override
  public void initialize(Settings settings, StorageEngine engine) {
    this.settings = settings;
    this.engine = engine;
  }

  @Override
  public void export() throws Exception {
    for (ODocument workitem : StorageQuery.getRTCWorkItems(engine)) {
      LOGGER.info("");
      LOGGER.info("===== WorkItem: " + workitem.field(FieldNames.ID) + " ======");
      for (Entry<String, Object> entry : workitem) {
        String formattedAttribute = String.format("%-25s: %s", entry.getKey(), entry.getValue());
        LOGGER.info(formattedAttribute);
      }
    }
  }

  @Override
  public boolean isConfigured() {
    return settings.isSystemOutExporterConfigured();
  }
}
