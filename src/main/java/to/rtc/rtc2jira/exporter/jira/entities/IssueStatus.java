package to.rtc.rtc2jira.exporter.jira.entities;

import java.net.URL;

import org.codehaus.jackson.map.annotate.JsonView;

public class IssueStatus extends NamedEntity {

  private String description;
  private URL iconUrl;
  private StatusCategory statusCategory;

  public static IssueStatus createDone() {
    IssueStatus issueStatus = new IssueStatus();
    issueStatus.setId(StatusEnum.done.getJiraId());
    issueStatus.setName(StatusEnum.done.getStatusName());
    issueStatus.setStatusCategory(StatusCategory.createDone());
    return issueStatus;
  }

  public static IssueStatus createInProgress() {
    IssueStatus issueStatus = new IssueStatus();
    issueStatus.setId(StatusEnum.inprogress.getJiraId());
    issueStatus.setName(StatusEnum.inprogress.getStatusName());
    issueStatus.setStatusCategory(StatusCategory.createInProgress());
    return issueStatus;
  }

  public static IssueStatus createToDo() {
    IssueStatus issueStatus = new IssueStatus();
    issueStatus.setId(StatusEnum.todo.getJiraId());
    issueStatus.setName(StatusEnum.todo.getStatusName());
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
  public StatusEnum getStatusEnum() {
    return StatusEnum.forJiraId(getId());
  }


}
