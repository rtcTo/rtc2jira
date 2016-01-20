package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.EnumSet;
import java.util.Optional;

public enum TimeSlotEnum implements SelectionTypeEnum {

  UNSET("timeslot.literal.l1", "-1"), //
  T2013_1("timeslot.literal.l9", "10210"), //
  T2013_2("timeslot.literal.l10", "10361"), //
  T2013_3("timeslot.literal.l2", "10362"), //
  T2014_1("timeslot.literal.l3", "10363"), //
  T2014_2("timeslot.literal.l4", "10364"), //
  T2014_3("timeslot.literal.l5", "10365"), //
  T2014_4("timeslot.literal.l11", "10366"), //
  T2015_1("timeslot.literal.l6", "10367"), //
  T2015_2("timeslot.literal.l7", "10368"), //
  T2015_3("timeslot.literal.l8", "10369"), //
  T2015_4("timeslot.literal.l12", "10370");

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
