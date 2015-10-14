package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.EnumSet;
import java.util.Optional;

public enum CustomerEnum implements SelectionTypeEnum {

  UNSET("customer.literal.l1", "EMPTY") {
    @Override
    public CustomFieldOption getCustomFieldOption() {
      return null;
    }
  }, //
  AGROLA("customer.literal.l2", "10440"), //
  Bison_Schweiz("customer.literal.l3", "10441"), //
  Bison_IT_Services("customer.literal.l4", "10442"), //
  Eichenberger_Gewinde("customer.literal.l5", "10443"), //
  Frigemo_Handelsfirmen("customer.literal.l6", "10444"), //
  Gero_Hoberg_Driesch("customer.literal.l7", "10445"), //
  Kowag("customer.literal.l8", "10446"), //
  Landi("customer.literal.l9", "10447"), //
  Landi_Schweiz("customer.literal.l10", "10448"), //
  Landesprodukte("customer.literal.l11", "10449"), //
  UFA("customer.literal.l12", "10450"), //
  Uvavins("customer.literal.l13", "10451"), //
  Wilhelm_Hoyer("customer.literal.l14", "10452"), //
  DRWZ("customer.literal.l15", "10453"), //
  König_Stahl("customer.literal.l16", "10454"), //
  Bogner_Edelstahl("customer.literal.l17", "10455"), //
  Inotex("customer.literal.l18", "10456"), //
  AMETRAS_INFORMATIK("customer.literal.l19", "10457"), //
  Fenaco("customer.literal.l20", "10458"), //
  Feldsaaten_Freudenberger("customer.literal.l23", "10459"), //
  RUAG_Environment("customer.literal.l24", "10460"), //
  Bison_Maxess("customer.literal.l25", "10461");

  private String rctId;
  private String jiraId;

  private CustomerEnum(String rctId, String jiraId) {
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
  public Optional<CustomerEnum> forJiraId(String jiraId) {
    EnumSet<CustomerEnum> all = EnumSet.allOf(CustomerEnum.class);
    return all.stream().filter(item -> item.getJiraId().equals(jiraId)).findFirst();
  }

  @SuppressWarnings("unchecked")
  public Optional<CustomerEnum> forRtcId(String rtcId) {
    EnumSet<CustomerEnum> all = EnumSet.allOf(CustomerEnum.class);
    return all.stream().filter(item -> item.getRtcId().equals(rtcId)).findFirst();
  }

}
