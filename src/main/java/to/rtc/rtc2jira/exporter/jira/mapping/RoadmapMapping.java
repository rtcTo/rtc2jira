/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.JiraRadioItem;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class RoadmapMapping implements Mapping {

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    if (Boolean.TRUE.equals(value)) {
      issue.getFields().setRoadmap(JiraRadioItem.YES_ROADMAP);
    } else {
      issue.getFields().setRoadmap(JiraRadioItem.NONE);
    }
  }
}
