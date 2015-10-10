/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.EnumSet;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueResolution;
import to.rtc.rtc2jira.exporter.jira.entities.ResolutionEnum;
import to.rtc.rtc2jira.exporter.jira.entities.Timetracking;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class EstimatedHoursMapping implements Mapping {

  static final EnumSet<ResolutionEnum> NO_REMAINING_TIME = EnumSet.of(ResolutionEnum.cannotReproduce,
      ResolutionEnum.done, ResolutionEnum.duplicate, ResolutionEnum.fixed, ResolutionEnum.wont_fix,
      ResolutionEnum.wontDo);

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    if (value != null) {
      int hours = ((Integer) value).intValue();
      int minutes = hours * 360;
      Timetracking timetracking = new Timetracking();
      timetracking.setOriginalEstimate(minutes + "m");
      IssueResolution resolution = issue.getFields().getResolution();
      if (resolution != null && NO_REMAINING_TIME.contains(resolution.getEnum())) {
        timetracking.setRemainingEstimate("0m");
      }
      issue.getFields().setTimetracking(timetracking);
    }
  }
}
