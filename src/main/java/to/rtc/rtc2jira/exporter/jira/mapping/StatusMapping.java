/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.StateEnum;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class StatusMapping implements Mapping {
  private static final Logger LOGGER = Logger.getLogger(StatusMapping.class.getName());

  @Override
  public void map(Entry<String, Object> attribute, Issue issue, StorageEngine storage) {
    String stateStr = (String) attribute.getValue();

    StateEnum.forRtcLiteral(stateStr).ifPresent(
        (stateEnum) -> issue.getFields().setStatus(stateEnum.getStatusEnum().getIssueStatus()));

    if (issue.getFields().getStatus() == null) {
      LOGGER.log(Level.SEVERE, "No mapping found for rtc state literal '" + stateStr + "'");
    }

  }
}
