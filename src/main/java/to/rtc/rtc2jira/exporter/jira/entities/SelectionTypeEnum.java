package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.Optional;

public interface SelectionTypeEnum {

  <T extends SelectionTypeEnum> Optional<T> forJiraId(String jiraId);

  <T extends SelectionTypeEnum> Optional<T> forRtcId(String rtcId);

  CustomFieldOption getCustomFieldOption();

  String getJiraId();

  String getRtcId();

}
