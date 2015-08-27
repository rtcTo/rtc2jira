package to.rtc.rtc2jira.exporter.jira.entities;

public enum CustomerPriorityEnum {
  high("priority.literal.l11", ""), medium("priority.literal.l07", ""), low("priority.literal.l02",
      ""), unclassified("priority.literal.l01", "");



  private String rtcId;
  private String jiraId;

  CustomerPriorityEnum(String rtcId, String jiraId) {
    this.rtcId = rtcId;
    this.jiraId = jiraId;
  }

}
