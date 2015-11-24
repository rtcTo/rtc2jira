package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.EnumSet;
import java.util.Optional;

public enum DevlDeadlineEnum implements SelectionTypeEnum {

  UNSET("devldeadline.literal.l2", "-1"), //
  FEN_14_5_20140728("devldeadline.literal.l1", "10400"), //
  FEN_14_6_20140908("devldeadline.literal.l3", "10401"), //
  BASE_14_5_20140714("devldeadline.literal.l4", "10402"), //
  BASE_14_6_20140825("devldeadline.literal.l5", "10403"), //
  BASE_14_7_20141020("devldeadline.literal.l6", "10404"), //
  BASE_14_8_20141215("devldeadline.literal.l7", "10405"), //
  KS_14_3_20140602_prov("devldeadline.literal.l8", "10406"), //
  LCH_14_4_2014("devldeadline.literal.l9", "10407"), //
  LANDI_14_4_20140616("devldeadline.literal.l10", "10408"), //
  LANDI_14_6_20140908("devldeadline.literal.l11", "10409"), //
  LANDI_14_8_20141117("devldeadline.literal.l12", "10410"), //
  LCH_14_5_2014("devldeadline.literal.l13", "10411"), //
  FEN_15_1_20150508("devldeadline.literal.l14", "10412"), //
  BASE_15_1_20150209("devldeadline.literal.l15", "10413"), //
  BASE_15_2_20150406("devldeadline.literal.l16", "10414"), //
  BASE_15_3_20150601("devldeadline.literal.l17", "10415"), //
  BASE_15_4_20150727("devldeadline.literal.l18", "10416"), //
  FEN_14_7_20140929("devldeadline.literal.l19", "10417"), //
  LCH_15_1_2015("devldeadline.literal.l20", "10418"), //
  LANDI_15_1_20150504("devldeadline.literal.l21", "10419"), //
  LANDI_15_2_20150907("devldeadline.literal.l22", "10420"), //
  LANDI_15_3_20151116("devldeadline.literal.l23", "10421"), //
  LCH_15_2_2015("devldeadline.literal.l24", "10422"), //
  LCH_15_3_2015("devldeadline.literal.l25", "10423"), //
  FEN_15_2("devldeadline.literal.l26", "10424"), //
  LCH_15_4_2015("devldeadline.literal.l27", "10601"), //
  BASE_15_5_20151116("devldeadline.literal.l28", "10602"), //
  BASE_15_6("devldeadline.literal.l29", "10603"), //
  FEN_16_1("devldeadline.literal.l30", "10604"), //
  LCH_16_1("devldeadline.literal.l31", "10605"), //
  LCH_16_2("devldeadline.literal.l32", "10606"), //
  LCH_16_3("devldeadline.literal.l33", "10607"), //
  FEN_16_2("devldeadline.literal.l34", "10608");

  private String rctId;
  private String jiraId;

  private DevlDeadlineEnum(String rctId, String jiraId) {
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
  public Optional<DevlDeadlineEnum> forJiraId(String jiraId) {
    EnumSet<DevlDeadlineEnum> all = EnumSet.allOf(DevlDeadlineEnum.class);
    return all.stream().filter(item -> item.getJiraId().equals(jiraId)).findFirst();
  }

  @SuppressWarnings("unchecked")
  public Optional<DevlDeadlineEnum> forRtcId(String rtcId) {
    EnumSet<DevlDeadlineEnum> all = EnumSet.allOf(DevlDeadlineEnum.class);
    return all.stream().filter(item -> item.getRtcId().equals(rtcId)).findFirst();
  }

}
