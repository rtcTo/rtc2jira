/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.SelectionTypeEnum;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public abstract class AbstractSelectionTypeMapping<T extends SelectionTypeEnum> implements Mapping {
  private static final Logger LOGGER = Logger.getLogger(AbstractSelectionTypeMapping.class.getName());

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    String projectId = (String) value;

    if (projectId == null || projectId.isEmpty()) {
      setFieldValue(issue, null);
    } else {
      Optional<T> forRtcId = getDefaultValue().forRtcId(projectId);
      forRtcId.ifPresent((theEnum) -> setFieldValue(issue, theEnum));

      if (!forRtcId.isPresent()) {
        LOGGER.log(Level.SEVERE, "No project mapping found for " + getDefaultValue().getClass().getName()
            + " literal '" + projectId + "'");
      }
    }


  }

  abstract protected void setFieldValue(Issue issue, T theEnum);

  abstract protected T getDefaultValue();

}
