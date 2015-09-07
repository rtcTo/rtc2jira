/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.Map.Entry;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueResolution;
import to.rtc.rtc2jira.exporter.jira.entities.ResolutionEnum;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class ResolutionMapping implements Mapping {

  @Override
  public void map(Entry<String, Object> attribute, Issue issue, StorageEngine storage) {
    Object resolution = attribute.getValue();
    if (resolution != null) {
      int id = Integer.parseInt((String) resolution);
      ResolutionEnum resEnum = ResolutionEnum.fromRtcId(id);
      if (resEnum != null) {
        issue.getFields().setResolution(new IssueResolution(resEnum));
      }
    }
  }
}
