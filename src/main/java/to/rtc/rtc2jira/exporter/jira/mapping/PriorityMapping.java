package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.ExportManager;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssuePriority;
import to.rtc.rtc2jira.exporter.jira.entities.PriorityEnum;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class PriorityMapping implements Mapping {
  static Logger LOGGER = Logger.getLogger(PriorityMapping.class.getName());
  static {
    LOGGER.addHandler(ExportManager.DEFAULT_LOG_HANDLER);
  }

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    if (!SeverityMapping.severityOverridesPriority(issue)) {
      String valueStr = (String) value;
      try {
        PriorityEnum priorityEnum = PriorityEnum.forRtcLiteral(valueStr);
        if (priorityEnum != null) {
          issue.getFields().setPriority(IssuePriority.createWithId(priorityEnum.getJiraId()));
        }
      } catch (IllegalArgumentException e) {
        LOGGER.log(Level.WARNING, "Could not recognize the priority literal '" + valueStr + "'");
      }
    }
  }
}
