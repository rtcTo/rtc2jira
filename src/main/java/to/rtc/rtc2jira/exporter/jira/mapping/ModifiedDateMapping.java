package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.Date;
import java.util.Map.Entry;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.StorageEngine;

public class ModifiedDateMapping implements Mapping {

  @Override
  public void map(Entry<String, Object> attribute, Issue issue, StorageEngine storage) {
    issue.getFields().setUpdated((Date) attribute.getValue());
  }
}
