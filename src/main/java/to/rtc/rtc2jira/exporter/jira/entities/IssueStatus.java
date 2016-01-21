package to.rtc.rtc2jira.exporter.jira.entities;

import java.net.URL;

import org.codehaus.jackson.map.annotate.JsonView;

public class IssueStatus extends NamedEntity {

  private String description;
  private URL iconUrl;
  private StatusCategory statusCategory;

  @Override
  public String getPath() {
    return "/status";
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public URL getIconUrl() {
    return iconUrl;
  }

  public void setIconUrl(URL iconUrl) {
    this.iconUrl = iconUrl;
  }

  public StatusCategory getStatusCategory() {
    return statusCategory;
  }

  public void setStatusCategory(StatusCategory statusCategory) {
    this.statusCategory = statusCategory;
  }

  @JsonView(IssueView.Filtered.class)
  public StateEnum getStatusEnum(IssueType issueType) {
    return StateEnum.forJiraId(getId(), issueType).orElse(null);
  }


  /**
   * TODO implement
   * 
   * @param issueType
   * @return
   */
  public static IssueStatus createStartingStatus(IssueType issueType) {
    return StateEnum.migr_new.getIssueStatus();
  }


}
