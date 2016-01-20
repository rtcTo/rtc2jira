package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.EnumSet;
import java.util.Optional;

public enum DevlDeadlineEnum implements SelectionTypeEnum {

  UNSET("devldeadline.literal.l2", "-1"), //
  FEN_14_5_20140728("devldeadline.literal.l1", "10203"), //
  FEN_14_6_20140908("devldeadline.literal.l3", "10329"), //
  BASE_14_5_20140714("devldeadline.literal.l4", "10330"), //
  BASE_14_6_20140825("devldeadline.literal.l5", "10331"), //
  BASE_14_7_20141020("devldeadline.literal.l6", "10332"), //
  BASE_14_8_20141215("devldeadline.literal.l7", "10333"), //
  KS_14_3_20140602_prov("devldeadline.literal.l8", "10334"), //
  LCH_14_4_2014("devldeadline.literal.l9", "10335"), //
  LANDI_14_4_20140616("devldeadline.literal.l10", "10336"), //
  LANDI_14_6_20140908("devldeadline.literal.l11", "10337"), //
  LANDI_14_8_20141117("devldeadline.literal.l12", "10338"), //
  LCH_14_5_2014("devldeadline.literal.l13", "10339"), //
  FEN_15_1_20150508("devldeadline.literal.l14", "10340"), //
  BASE_15_1_20150209("devldeadline.literal.l15", "10341"), //
  BASE_15_2_20150406("devldeadline.literal.l16", "10342"), //
  BASE_15_3_20150601("devldeadline.literal.l17", "10343"), //
  BASE_15_4_20150727("devldeadline.literal.l18", "10344"), //
  FEN_14_7_20140929("devldeadline.literal.l19", "10345"), //
  LCH_15_1_2015("devldeadline.literal.l20", "10346"), //
  LANDI_15_1_20150504("devldeadline.literal.l21", "10347"), //
  LANDI_15_2_20150907("devldeadline.literal.l22", "10348"), //
  LANDI_15_3_20151116("devldeadline.literal.l23", "10349"), //
  LCH_15_2_2015("devldeadline.literal.l24", "10350"), //
  LCH_15_3_2015("devldeadline.literal.l25", "10351"), //
  FEN_15_2("devldeadline.literal.l26", "10352"), //
  LCH_15_4_2015("devldeadline.literal.l27", "10353"), //
  BASE_15_5_20151116("devldeadline.literal.l28", "10354"), //
  BASE_15_6("devldeadline.literal.l29", "10355"), //
  FEN_16_1("devldeadline.literal.l30", "10356"), //
  LCH_16_1("devldeadline.literal.l31", "10357"), //
  LCH_16_2("devldeadline.literal.l32", "10358"), //
  LCH_16_3("devldeadline.literal.l33", "10359"), //
  FEN_16_2("devldeadline.literal.l34", "10360");

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
