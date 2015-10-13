package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.StorageEngine;

public class TestScriptIdsMapping implements Mapping {

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    String scriptIds = (String) value;
    if (scriptIds != null && !scriptIds.isEmpty()) {
      issue.getFields().setTestScriptIds(scriptIds);
    }
  }
}
