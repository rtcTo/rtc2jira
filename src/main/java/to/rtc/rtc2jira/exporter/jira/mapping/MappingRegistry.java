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
    registry.put(FieldNames.SUMMARY, new SummaryMapping());
  }

  public void map(Entry<String, Object> attribute, Issue issue, StorageEngine storage) {
    Mapping mapping = registry.get(attribute.getKey());
    if (mapping != null) {
      mapping.map(attribute, issue, storage);
    }
  }

}
