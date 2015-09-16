/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class ArchivedMapping implements Mapping {

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    if (Boolean.FALSE.equals(value)) {
      issue.getFields().setArchived(false);
    } else {
      issue.getFields().setArchived(true);
    }
  }
}
