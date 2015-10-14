/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.ExportManager;
import to.rtc.rtc2jira.exporter.jira.entities.BisonProjectEnum;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class BisonProjectNameMapping implements Mapping {
  private static final Logger LOGGER = Logger.getLogger(BisonProjectNameMapping.class.getName());
  static {
    LOGGER.addHandler(ExportManager.DEFAULT_LOG_HANDLER);
  }

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    String projectId = (String) value;

    if (projectId == null || projectId.isEmpty()) {
      issue.getFields().setBisonProjectName(BisonProjectEnum.UNSET.getCustomFieldOption());
    } else {
      BisonProjectEnum.forRtcId(projectId).ifPresent(
          (projectEnum) -> issue.getFields().setBisonProjectName(projectEnum.getCustomFieldOption()));

      if (issue.getFields().getStatus() == null) {
        LOGGER.log(Level.SEVERE, "No project mapping found for rtc project name literal '" + projectId + "'");
      }
    }


  }
}
