package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.EnumSet;
import java.util.Optional;

public enum RealisedByIdmEnum implements SelectionTypeEnum {

  UNSET("realisebyidm.literal.l1", "-1"), //
  IDM_Retail("realisebyidm.literal.l2", "10463"), //
  IDM_Weinhandel("realisebyidm.literal.l3", "10464"), //
  IDM_Basis_branchenneutral("realisebyidm.literal.l4", "10465"), //
  IDM_Saatgut("realisebyidm.literal.l5", "10466"), //
  IDM_Brenn_und_Treibstoff("realisebyidm.literal.l6", "10467");

  private String rctId;
  private String jiraId;

  private RealisedByIdmEnum(String rctId, String jiraId) {
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
  public Optional<RealisedByIdmEnum> forJiraId(String jiraId) {
    EnumSet<RealisedByIdmEnum> all = EnumSet.allOf(RealisedByIdmEnum.class);
    return all.stream().filter(item -> item.getJiraId().equals(jiraId)).findFirst();
  }

  @SuppressWarnings("unchecked")
  public Optional<RealisedByIdmEnum> forRtcId(String rtcId) {
    EnumSet<RealisedByIdmEnum> all = EnumSet.allOf(RealisedByIdmEnum.class);
    return all.stream().filter(item -> item.getRtcId().equals(rtcId)).findFirst();
  }

}
