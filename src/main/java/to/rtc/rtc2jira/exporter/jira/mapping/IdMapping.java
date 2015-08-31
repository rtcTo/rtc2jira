package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.Map.Entry;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.StorageEngine;

public class IdMapping implements Mapping {

  @Override
  public void map(Entry<String, Object> attribute, Issue issue, StorageEngine storage) {
    String id = (String) attribute.getValue();
    issue.setId(id);
  }
}
