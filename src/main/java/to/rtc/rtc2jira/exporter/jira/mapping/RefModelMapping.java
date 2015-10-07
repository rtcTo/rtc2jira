/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.RefModelEnum;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class RefModelMapping implements Mapping {
  private static final Logger LOGGER = Logger.getLogger(RefModelMapping.class.getName());

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    String projectId = (String) value;

    if (projectId == null || projectId.isEmpty()) {
      issue.getFields().setRefModel(null);
    } else {
      RefModelEnum.forRtcId(projectId).ifPresent(
          (refModelEnum) -> issue.getFields().setRefModel(refModelEnum.getCustomFieldOption()));

      if (issue.getFields().getStatus() == null) {
        LOGGER.log(Level.SEVERE, "No project mapping found for rtc project name literal '" + projectId + "'");
      }
    }


  }
}
