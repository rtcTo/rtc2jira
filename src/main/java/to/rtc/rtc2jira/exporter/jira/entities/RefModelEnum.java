package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.EnumSet;
import java.util.Optional;

public enum RefModelEnum {

  Standard("referencemodelenum.literal.l2", "10356"), //
  AFB("referencemodelenum.literal.l4", "10357"), //
  AMK("referencemodelenum.literal.l6", "10358"), //
  BM1000("referencemodelenum.literal.l8", "10359"), //
  BM3000("referencemodelenum.literal.l10", "10360"), //
  BM4000("referencemodelenum.literal.l12", "10361"), //
  DRWZ("referencemodelenum.literal.l14", "10362"), //
  EAG("referencemodelenum.literal.l16", "10363"), //
  Gero("referencemodelenum.literal.l18", "10364"), //
  Inotex("referencemodelenum.literal.l20", "10365"), //
  KSG("referencemodelenum.literal.l22", "10366"), //
  RAgr("referencemodelenum.literal.l24", "10367"), //
  RFen("referencemodelenum.literal.l26", "10368"), //
  RGof("referencemodelenum.literal.l28", "10369"), //
  RLap("referencemodelenum.literal.l30", "10370"), //
  RLch("referencemodelenum.literal.l32", "10371"), //
  RLdi("referencemodelenum.literal.l34", "10372"), //
  RUfa("referencemodelenum.literal.l36", "10373"), //
  TSH("referencemodelenum.literal.l38", "10374"), //
  TGH("referencemodelenum.literal.l40", "10375"), //
  TWS("referencemodelenum.literal.l43", "10376");

  private String rctId;
  private String jiraId;

  private RefModelEnum(String rctId, String jiraId) {
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

  public static final Optional<RefModelEnum> forJiraId(String jiraId) {
    EnumSet<RefModelEnum> all = EnumSet.allOf(RefModelEnum.class);
    return all.stream().filter(item -> item.getJiraId().equals(jiraId)).findFirst();
  }

  public static final Optional<RefModelEnum> forRtcId(String rtcId) {
    EnumSet<RefModelEnum> all = EnumSet.allOf(RefModelEnum.class);
    return all.stream().filter(item -> item.getRctId().equals(rtcId)).findFirst();
  }


}
