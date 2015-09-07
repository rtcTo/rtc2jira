package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.EnumSet;

public enum ResolutionEnum {
  fixed(1, 1, -1), wont_fix(5, 2, -1), duplicate(2, 3, -1), incomplete(-1, 4, -1), cannotReproduce(4, 5, -1), done(-1,
      10000, -1), wontDo(3, 10001, -1);


  final private int rtcId;
  final private int jiraId;
  final private int bugzillaId;

  ResolutionEnum(int rtcId, int jiraId, int bugzillaId) {
    this.rtcId = rtcId;
    this.jiraId = jiraId;
    this.bugzillaId = bugzillaId;
  }

  public int getRtcId() {
    return rtcId;
  }

  public int getJiraId() {
    return jiraId;
  }

  public int getBugzillaId() {
    return bugzillaId;
  }


  public static ResolutionEnum fromRtcId(int rtcId) {
    EnumSet<ResolutionEnum> all = EnumSet.allOf(ResolutionEnum.class);
    return all.stream().filter(item -> item.getRtcId() == rtcId).findFirst().get();
  }

  public static ResolutionEnum fromJiraId(int jiraId) {
    EnumSet<ResolutionEnum> all = EnumSet.allOf(ResolutionEnum.class);
    return all.stream().filter(item -> item.getJiraId() == jiraId).findFirst().get();
  }

}
