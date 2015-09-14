package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.Date;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class DueDateMapping implements Mapping {

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    issue.getFields().setDuedate((Date) value);
  }
}
