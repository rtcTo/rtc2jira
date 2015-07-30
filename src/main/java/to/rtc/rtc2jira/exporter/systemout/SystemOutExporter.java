/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.systemout;

import java.util.List;
import java.util.Map.Entry;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.storage.StorageEngine;
import to.rtc.rtc2jira.storage.WorkItemConstants;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

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
    engine.withDB(db -> {
      OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<ODocument>("select * from WorkItem");
      List<ODocument> workItems = db.query(query);
      for (ODocument workitem : workItems) {
        System.out.println();
        System.out.println("===== WorkItem: " + workitem.field(WorkItemConstants.ID) + " ======");
        for (Entry<String, Object> entry : workitem) {
          String formattedAttribute = String.format("%-25s: %s", entry.getKey(), entry.getValue());
          System.out.println(formattedAttribute);
        }
      }
    });
  }

  @Override
  public boolean isConfigured() {
    return settings.isSystemOutExporterConfigured();
  }
}
