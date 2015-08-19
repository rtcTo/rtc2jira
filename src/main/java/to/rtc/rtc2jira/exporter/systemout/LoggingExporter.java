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

import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * @author roman.schaller
 *
 */
public class LoggingExporter implements Exporter {
  private static final Logger LOGGER = Logger.getLogger(LoggingExporter.class.getName());

  private Settings settings;

  @Override
  public void initialize(Settings settings, StorageEngine engine) {
    this.settings = settings;
  }

  @Override
  public boolean isConfigured() {
    return settings.isSystemOutExporterConfigured();
  }

  @Override
  public void createOrUpdateItem(ODocument item) throws Exception {
    createItem(item);
  }

  private void createItem(ODocument item) throws Exception {
    LOGGER.info("");
    LOGGER.info("===== WorkItem: " + item.field(FieldNames.ID) + " ======");
    for (Entry<String, Object> entry : item) {
      String formattedAttribute = String.format("%-25s: %s", entry.getKey(), entry.getValue());
      LOGGER.info(formattedAttribute);
    }
  }

}
