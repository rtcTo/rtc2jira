package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class StoryPointsMapping implements Mapping {

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    if (value == null) {
      issue.getFields().setStoryPoints(null);
    } else {
      int points = Integer.parseInt((String) value);
      if (points <= 0) {
        issue.getFields().setStoryPoints(null);
      } else {
        issue.getFields().setStoryPoints(points);
      }
    }
  }
}
