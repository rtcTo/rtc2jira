package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.EnumSet;
import java.util.Optional;

public enum CustomerEnum implements SelectionTypeEnum {

  UNSET("customer.literal.l1", "-1"), //
  AGROLA("customer.literal.l2", "10202"), //
  Bison_Schweiz("customer.literal.l3", "10371"), //
  Bison_IT_Services("customer.literal.l4", "10372"), //
  Eichenberger_Gewinde("customer.literal.l5", "10373"), //
  Frigemo_Handelsfirmen("customer.literal.l6", "10374"), //
  Gero_Hoberg_Driesch("customer.literal.l7", "10375"), //
  Kowag("customer.literal.l8", "10376"), //
  Landi("customer.literal.l9", "10377"), //
  Landi_Schweiz("customer.literal.l10", "10378"), //
  Landesprodukte("customer.literal.l11", "10379"), //
  UFA("customer.literal.l12", "10380"), //
  Uvavins("customer.literal.l13", "10381"), //
  Wilhelm_Hoyer("customer.literal.l14", "10382"), //
  DRWZ("customer.literal.l15", "10383"), //
  König_Stahl("customer.literal.l16", "10384"), //
  Bogner_Edelstahl("customer.literal.l17", "10385"), //
  Inotex("customer.literal.l18", "10386"), //
  AMETRAS_INFORMATIK("customer.literal.l19", "10387"), //
  Fenaco("customer.literal.l20", "10388"), //
  Feldsaaten_Freudenberger("customer.literal.l23", "10389"), //
  RUAG_Environment("customer.literal.l24", "10390"), //
  Bison_Maxess("customer.literal.l25", "10391");

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
