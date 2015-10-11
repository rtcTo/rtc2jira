package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.EnumSet;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum CompetenceCenterEnum {

  UNSET("competencecenter.literal.l1", "EMPTY") {
    public CustomFieldOption getCustomFieldOption() {
      return null;
    }
  }, //
  CC1_RETAIL("competencecenter.literal.l2", "10378"), //
  CC2_WHOLESALE("competencecenter.literal.l3", "10379"), //
  CC3_FLG("competencecenter.literal.l4", "10380"), //
  CC4_TECH("competencecenter.literal.l5", "10381"), //
  CC5_OPERATIONAL("competencecenter.literal.l6", "10382");

  private String rctId;
  private String jiraId;
  static private final Logger LOGGER = Logger.getLogger(CompetenceCenterEnum.class.getName());

  private CompetenceCenterEnum(String rctId, String jiraId) {
    this.rctId = rctId;
    this.jiraId = jiraId;
  }

  public String getRctId() {
    return rctId;
  }

  public void setRctId(String rctId) {
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

  public static final Optional<CompetenceCenterEnum> forJiraId(String jiraId) {
    EnumSet<CompetenceCenterEnum> all = EnumSet.allOf(CompetenceCenterEnum.class);
    Optional<CompetenceCenterEnum> first = all.stream().filter(item -> item.getJiraId().equals(jiraId)).findFirst();
    if (!first.isPresent()) {
      LOGGER.log(Level.SEVERE, "Could not find a CompetenceCenterEnum entry for the jira id " + jiraId);
    }
    return first;
  }

  public static final Optional<CompetenceCenterEnum> forRtcId(String rtcId) {
    EnumSet<CompetenceCenterEnum> all = EnumSet.allOf(CompetenceCenterEnum.class);
    Optional<CompetenceCenterEnum> first = all.stream().filter(item -> item.getRctId().equals(rtcId)).findFirst();
    if (!first.isPresent()) {
      LOGGER.log(Level.SEVERE, "Could not find a CompetenceCenterEnum entry for the rtc id " + rtcId);
    }
    return first;
  }


}
