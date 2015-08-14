/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.systemout;

import java.util.Map.Entry;

import com.orientechnologies.orient.core.record.impl.ODocument;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;
import to.rtc.rtc2jira.storage.StorageQuery;

/**
 * @author roman.schaller
 *
 */
public class SystemOutExporter implements Exporter {

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
      System.out.println();
      System.out.println("===== WorkItem: " + workitem.field(FieldNames.ID) + " ======");
      for (Entry<String, Object> entry : workitem) {
        String formattedAttribute = String.format("%-25s: %s", entry.getKey(), entry.getValue());
        System.out.println(formattedAttribute);
      }
    }
  }

  @Override
  public boolean isConfigured() {
    return settings.isSystemOutExporterConfigured();
  }
}
