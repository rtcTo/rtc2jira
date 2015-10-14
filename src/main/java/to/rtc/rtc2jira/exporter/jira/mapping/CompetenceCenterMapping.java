/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.ExportManager;
import to.rtc.rtc2jira.exporter.jira.entities.CompetenceCenterEnum;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class CompetenceCenterMapping implements Mapping {
  private static final Logger LOGGER = Logger.getLogger(CompetenceCenterMapping.class.getName());
  static {
    LOGGER.addHandler(ExportManager.DEFAULT_LOG_HANDLER);
  }

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    String literal = (String) value;

    if (literal == null || literal.isEmpty()) {
      issue.getFields().setCompetenceCenter(null);
    } else {
      CompetenceCenterEnum.forRtcId(literal).ifPresent(
          (competenceCenterEnum) -> issue.getFields().setCompetenceCenter(competenceCenterEnum.getCustomFieldOption()));

      if (issue.getFields().getStatus() == null) {
        LOGGER.log(Level.SEVERE, "No competence center mapping found for rtc project name literal '" + literal + "'");
      }
    }


  }
}
