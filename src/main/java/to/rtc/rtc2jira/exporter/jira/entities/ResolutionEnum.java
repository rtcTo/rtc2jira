package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.EnumSet;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.ExportManager;

public enum ResolutionEnum {
  fixed(1, 1, -1), wont_fix(5, 2, -1), duplicate(2, 3, -1), incomplete(-1, 4, -1), cannotReproduce(4, 5, -1), done(-1,
      10000, -1), wontDo(3, 10001, -1);


  final private int rtcId;
  final private int jiraId;
  final private int bugzillaId;
  static private final Logger LOGGER = Logger.getLogger(ResolutionEnum.class.getName());
  static {
    LOGGER.addHandler(ExportManager.DEFAULT_LOG_HANDLER);
  }

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
    Optional<ResolutionEnum> first = all.stream().filter(item -> item.getRtcId() == rtcId).findFirst();
    if (!first.isPresent()) {
      LOGGER.log(Level.SEVERE, "Could not find a ResolutionEnum entry for the rtc id " + rtcId);
    }
    return first.get();
  }

  public static ResolutionEnum fromJiraId(int jiraId) {
    EnumSet<ResolutionEnum> all = EnumSet.allOf(ResolutionEnum.class);
    Optional<ResolutionEnum> first = all.stream().filter(item -> item.getJiraId() == jiraId).findFirst();
    if (!first.isPresent()) {
      LOGGER.log(Level.SEVERE, "Could not find a ResolutionEnum entry for the jira id " + jiraId);
    }
    return first.get();
  }

}
