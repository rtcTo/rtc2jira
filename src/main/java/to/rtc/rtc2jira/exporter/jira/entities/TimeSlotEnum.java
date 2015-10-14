package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.EnumSet;
import java.util.Optional;

public enum TimeSlotEnum implements SelectionTypeEnum {

  UNSET("timeslot.literal.l1", "-1"), //
  T2013_1("timeslot.literal.l9", "10425"), //
  T2013_2("timeslot.literal.l10", "10428"), //
  T2013_3("timeslot.literal.l2", "10429"), //
  T2014_1("timeslot.literal.l3", "10430"), //
  T2014_2("timeslot.literal.l4", "10431"), //
  T2014_3("timeslot.literal.l5", "10432"), //
  T2014_4("timeslot.literal.l11", "10433"), //
  T2015_1("timeslot.literal.l6", "10434"), //
  T2015_2("timeslot.literal.l7", "10435"), //
  T2015_3("timeslot.literal.l8", "10436"), //
  T2015_4("timeslot.literal.l12", "10437");

  private String rctId;
  private String jiraId;

  private TimeSlotEnum(String rctId, String jiraId) {
    this.rctId = rctId;
    this.jiraId = jiraId;
  }

  public String getRtcId() {
    return rctId;
  }

  public void setRtcId(String rctId) {
    this.rctId = rctId;
  }

  public String getJiraId() {
    return jiraId;
  }

  public void setJiraId(String jiraId) {
    this.jiraId = jiraId;
  }

  public CustomFieldOption getCustomFieldOption() {
    return new CustomFieldOption(getJiraId());
  }

  @SuppressWarnings("unchecked")
  public Optional<TimeSlotEnum> forJiraId(String jiraId) {
    EnumSet<TimeSlotEnum> all = EnumSet.allOf(TimeSlotEnum.class);
    return all.stream().filter(item -> item.getJiraId().equals(jiraId)).findFirst();
  }

  @SuppressWarnings("unchecked")
  public Optional<TimeSlotEnum> forRtcId(String rtcId) {
    EnumSet<TimeSlotEnum> all = EnumSet.allOf(TimeSlotEnum.class);
    return all.stream().filter(item -> item.getRtcId().equals(rtcId)).findFirst();
  }

}
