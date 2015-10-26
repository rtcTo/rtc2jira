package to.rtc.rtc2jira.exporter.jira.entities;

import java.net.URL;

import org.codehaus.jackson.map.annotate.JsonView;

public class IssueStatus extends NamedEntity {

  private String description;
  private URL iconUrl;
  private StatusCategory statusCategory;

  public static IssueStatus createDone() {
    IssueStatus issueStatus = new IssueStatus();
    issueStatus.setId(StateEnum.bug_verified.getJiraId());
    issueStatus.setName(StateEnum.bug_verified.getStatusName());
    issueStatus.setStatusCategory(StatusCategory.createDone());
    return issueStatus;
  }

  public static IssueStatus createInProgress() {
    IssueStatus issueStatus = new IssueStatus();
    issueStatus.setId(StateEnum.bug_inProgress.getJiraId());
    issueStatus.setName(StateEnum.bug_inProgress.getStatusName());
    issueStatus.setStatusCategory(StatusCategory.createInProgress());
    return issueStatus;
  }

  public static IssueStatus createToDo() {
    IssueStatus issueStatus = new IssueStatus();
    issueStatus.setId(StateEnum.bug_neww.getJiraId());
    issueStatus.setName(StateEnum.bug_neww.getStatusName());
    issueStatus.setStatusCategory(StatusCategory.createToDo());
    return issueStatus;
  }

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
    return StateEnum.bug_neww.getIssueStatus();
  }


}
