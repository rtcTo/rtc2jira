/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueType;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author roman.schaller
 *
 */
public class SummaryMapping implements Mapping {

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    String summary = (String) value;
    issue.getFields().setSummary(summary);

    if (IssueType.EPIC.equals(issue.getFields().getIssuetype())) {
      issue.getFields().setEpicName(summary);
    }
  }
}
