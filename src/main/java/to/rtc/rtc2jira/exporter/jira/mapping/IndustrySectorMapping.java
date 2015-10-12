/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.List;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class IndustrySectorMapping implements Mapping {

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    @SuppressWarnings("unchecked")
    List<String> labels = (List<String>) value;
    issue.getFields().setIndustrySector(labels);
  }
}
