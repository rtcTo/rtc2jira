/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author roman.schaller
 *
 */
public class MappingRegistry {

  private final Map<String, Mapping> registry = new HashMap<>();

  public MappingRegistry() {
    registry.put(FieldNames.ID, new IdMapping());
    registry.put(FieldNames.SUMMARY, new SummaryMapping());
    registry.put(FieldNames.DUE_DATE, new DueDateMapping());
    registry.put(FieldNames.DESCRIPTION, new DescriptionMapping());
    registry.put(FieldNames.WORK_ITEM_TYPE, new WorkItemTypeMapping());
    registry.put(FieldNames.RESOLUTION_DATE, new ResolutiondateMapping());
    registry.put(FieldNames.RESOLUTION, new ResolutionMapping());
    registry.put(FieldNames.CREATOR, new ReporterMapping());
  }

  public void map(Entry<String, Object> attribute, Issue issue, StorageEngine storage) {
    Mapping mapping = registry.get(attribute.getKey());
    if (mapping != null) {
      mapping.map(attribute, issue, storage);
    }
  }

}
