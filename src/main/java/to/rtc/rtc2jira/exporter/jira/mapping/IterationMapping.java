/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.ExportManager;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueFields;
import to.rtc.rtc2jira.exporter.jira.entities.Version;
import to.rtc.rtc2jira.importer.mapping.TargetMapping;
import to.rtc.rtc2jira.importer.mapping.TargetMapping.IterationInfo;
import to.rtc.rtc2jira.importer.mapping.TargetMapping.RtcIterationType;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class IterationMapping implements Mapping {
  static Logger LOGGER = Logger.getLogger(IterationMapping.class.getName());
  static {
    LOGGER.addHandler(ExportManager.DEFAULT_LOG_HANDLER);
  }

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    IterationInfo iterInfo = new IterationInfo();
    if (value != null && !TargetMapping.NO_ITERATION.equals(value)) {
      try {
        iterInfo.unmarshall((String) value);
        IssueFields fields = issue.getFields();
        IterationHandler iterationHandler = IterationHandler.INSTANCE;
        fields.setPlannedFor(iterationHandler.getIterationQName(iterInfo));
        IterationInfo versionIterInfo = getVersionIteration(iterInfo);
        if (versionIterInfo != null) {
          Version version = iterationHandler.getVersion(versionIterInfo, fields.getProject());
          fields.getFixVersions().add(version);
        }
      } catch (IOException e) {
        LOGGER.log(Level.SEVERE, "Could not unmarshall Iteration json: " + e.getMessage());
      }
    }
  }

  IterationInfo getVersionIteration(IterationInfo iterInfo) {
    IterationInfo result = null;
    IterationInfo current = iterInfo;
    // release
    while (current != null) {
      if (current.iterationType == RtcIterationType.release) {
        result = current;
        break;
      }
      current = current.parent;
    }
    // time slot
    if (result == null) {
      current = iterInfo;
      while (current != null) {
        if (current.iterationType == RtcIterationType.timeslot) {
          result = current;
          break;
        }
        current = current.parent;
      }
    }
    return result;
  }


}
