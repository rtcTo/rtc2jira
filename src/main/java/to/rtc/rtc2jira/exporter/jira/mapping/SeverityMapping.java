package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssuePriority;
import to.rtc.rtc2jira.exporter.jira.entities.SeverityEnum;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class SeverityMapping implements Mapping {

  static public boolean severityOverridesPriority(Issue issue) {
    return "1".equals(issue.getFields().getIssuetype().getId());
  }

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    if (severityOverridesPriority(issue)) {
      try {
        String valueStr = (String) value;
        SeverityEnum severityEnum = SeverityEnum.fromRtcLiteral(valueStr);
        issue.getFields().setPriority(IssuePriority.createWithId(severityEnum.getJiraPriorityId()));
      } catch (IllegalArgumentException e) {
        // leave priority unset. will be handled with PriorityMapper
      }
    }
  }
}
