package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.EnumSet;
import java.util.Optional;

public enum ImplementationLevelEnum implements SelectionTypeEnum {

  Standard_Basis_Grosshandel("implementationlevel.literal.l1", "10468"), //
  BM1000_Steel("implementationlevel.literal.l2", "10469"), //
  BM3000_Retail("implementationlevel.literal.l3", "10470"), //
  BM4000_Production("implementationlevel.literal.l4", "10471"), //
  Customer_Individual("implementationlevel.literal.l5", "10472"), //
  Königstahl_Bogner("implementationlevel.literal.l6", "10473"), //
  Gero_H_u_D("implementationlevel.literal.l7", "10474"), //
  Landesprodukte_Agrola("implementationlevel.literal.l8", "10475"), //
  Landi("implementationlevel.literal.l9", "10476"), //
  Landi_Schweiz("implementationlevel.literal.l10", "10477");

  private String rctId;
  private String jiraId;

  private ImplementationLevelEnum(String rctId, String jiraId) {
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
  public Optional<ImplementationLevelEnum> forJiraId(String jiraId) {
    EnumSet<ImplementationLevelEnum> all = EnumSet.allOf(ImplementationLevelEnum.class);
    return all.stream().filter(item -> item.getJiraId().equals(jiraId)).findFirst();
  }

  @SuppressWarnings("unchecked")
  public Optional<ImplementationLevelEnum> forRtcId(String rtcId) {
    EnumSet<ImplementationLevelEnum> all = EnumSet.allOf(ImplementationLevelEnum.class);
    return all.stream().filter(item -> item.getRtcId().equals(rtcId)).findFirst();
  }

}
