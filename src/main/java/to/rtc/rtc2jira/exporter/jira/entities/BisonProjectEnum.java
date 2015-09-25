package to.rtc.rtc2jira.exporter.jira.entities;

public enum BisonProjectEnum {

  Ametras("", "10200"), App_Handelsvertreter("", "10201"), B4AGROLA_Betreuung("", "10202"), B4GOF_Trading("", "10203");


  private String rctId;
  private String jiraId;

  private BisonProjectEnum(String rctId, String jiraId) {
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


}
