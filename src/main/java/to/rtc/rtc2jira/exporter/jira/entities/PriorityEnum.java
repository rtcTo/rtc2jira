package to.rtc.rtc2jira.exporter.jira.entities;

public enum PriorityEnum {

  highest("1", "priority.literal.l11"), high("2", "priority.literal.l11"), medium("3", "priority.literal.l07"), low(
      "4", "priority.literal.l02"), lowest("5", "priority.literal.l02");

  private PriorityEnum(String jiraId, String rtcLiteral) {
    this.jiraId = jiraId;
    this.rtcLiteral = rtcLiteral;
  }

  private String jiraId;
  private String rtcLiteral;

  public String getJiraId() {
    return jiraId;
  }

  public String getRtcLiteral() {
    return rtcLiteral;
  }

  IssuePriority createIssuePriority() {
    return IssuePriority.createWithId(getJiraId());
  }

  /**
   * Hint: returns null for rtc literal of Undefined
   * 
   * @param literal
   * @return
   */
  public static PriorityEnum forRtcLiteral(String literal) {
    if (highest.getRtcLiteral().equals(literal)) {
      return highest;
    } else if (medium.getRtcLiteral().equals(literal)) {
      return medium;
    } else if (low.getRtcLiteral().equals(literal)) {
      return low;
    } else if ("priority.literal.l01".equals(literal)) {
      return null;
    } else {
      throw new IllegalArgumentException("No enumeration value found corresponding to the RTC priority literal '"
          + literal + "'");
    }
  }


}
