package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.StorageEngine;

public class IdMapping implements Mapping {

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    String id = (String) value;
    issue.setId(id);
  }
}
