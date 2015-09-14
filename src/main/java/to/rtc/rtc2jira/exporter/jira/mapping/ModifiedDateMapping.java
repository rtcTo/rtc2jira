package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.Date;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.StorageEngine;

public class ModifiedDateMapping implements Mapping {

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    issue.getFields().setUpdated((Date) value);
  }
}
