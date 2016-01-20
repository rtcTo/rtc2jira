package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.EnumSet;
import java.util.Optional;

public enum RefModelEnum implements SelectionTypeEnum {

  Standard("referencemodelenum.literal.l2", "10208"), //
  AFB("referencemodelenum.literal.l4", "10309"), //
  AMK("referencemodelenum.literal.l6", "10310"), //
  BM1000("referencemodelenum.literal.l8", "10311"), //
  BM3000("referencemodelenum.literal.l10", "10312"), //
  BM4000("referencemodelenum.literal.l12", "10313"), //
  DRWZ("referencemodelenum.literal.l14", "10314"), //
  EAG("referencemodelenum.literal.l16", "10315"), //
  Gero("referencemodelenum.literal.l18", "10316"), //
  Inotex("referencemodelenum.literal.l20", "10317"), //
  KSG("referencemodelenum.literal.l22", "10318"), //
  RAgr("referencemodelenum.literal.l24", "10319"), //
  RFen("referencemodelenum.literal.l26", "10320"), //
  RGof("referencemodelenum.literal.l28", "10321"), //
  RLap("referencemodelenum.literal.l30", "10322"), //
  RLch("referencemodelenum.literal.l32", "10323"), //
  RLdi("referencemodelenum.literal.l34", "10324"), //
  RUfa("referencemodelenum.literal.l36", "10325"), //
  TSH("referencemodelenum.literal.l38", "10326"), //
  TGH("referencemodelenum.literal.l40", "10327"), //
  TWS("referencemodelenum.literal.l43", "10328");

  private String rctId;
  private String jiraId;

  private RefModelEnum(String rctId, String jiraId) {
    this.rctId = rctId;
    this.jiraId = jiraId;
  }

  @Override
  public String getRtcId() {
    return rctId;
  }

  public void setRctId(String rctId) {
    this.rctId = rctId;
  }

  @Override
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
  @Override
  public Optional<RefModelEnum> forJiraId(String jiraId) {
    EnumSet<RefModelEnum> all = EnumSet.allOf(RefModelEnum.class);
    return all.stream().filter(item -> item.getJiraId().equals(jiraId)).findFirst();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Optional<RefModelEnum> forRtcId(String rtcId) {
    EnumSet<RefModelEnum> all = EnumSet.allOf(RefModelEnum.class);
    return all.stream().filter(item -> item.getRtcId().equals(rtcId)).findFirst();
  }

}
