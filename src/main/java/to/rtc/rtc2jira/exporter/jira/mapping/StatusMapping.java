/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.ExportManager;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueType;
import to.rtc.rtc2jira.exporter.jira.entities.StateEnum;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class StatusMapping implements Mapping {
  private static final Logger LOGGER = Logger.getLogger(StatusMapping.class.getName());
  static {
    LOGGER.addHandler(ExportManager.DEFAULT_LOG_HANDLER);
  }

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    String stateStr = (String) value;

    IssueType issueType = issue.getFields().getIssuetype();
    StateEnum.forRtcId(stateStr, issueType).ifPresent(
        (stateEnum) -> issue.getFields().setStatus(stateEnum.getIssueStatus()));

    if (issue.getFields().getStatus() == null) {
      LOGGER.log(Level.SEVERE, "No mapping found for rtc state literal '" + stateStr + "'");
    }

  }
}
