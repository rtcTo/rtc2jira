package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonView;

import to.rtc.rtc2jira.storage.Comment;

@XmlRootElement
public class JiraUser extends NamedEntity {


  static final public JiraUser createFromComment(Comment comment) {
    JiraUser jiraUser = new JiraUser();
    String[] segs = comment.getCreatorEmail().toLowerCase().split("@");
    jiraUser.setKey(segs[0]);
    jiraUser.setName(segs[0]);
    jiraUser.setDisplayName(comment.getCreaterName());
    jiraUser.setEmailAddress(comment.getCreatorEmail().toLowerCase());
    jiraUser.setActive(false);
    return jiraUser;
  }


  private String emailAddress;
  private String password = "bison2015";
  private String displayName;
  private boolean active = false;
  private String timeZone;


  @Override
  @JsonView(IssueView.Filtered.class)
  public String getPath() {
    return "/user";
  }

  @Override
  @JsonView(IssueView.Filtered.class)
  public String getSelfPath() {
    return getPath() + "?username=" + getName();
  }


  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress.toLowerCase();
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public boolean getActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @JsonView(IssueView.Read.class)
  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  @JsonView(IssueView.Filtered.class)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public int hashCode() {
    int prime = 31;
    int result = 1;
    result = result * prime + getName().hashCode();
    return result;
  }

  @Override
  public boolean equals(Object arg0) {
    if (arg0 == this) {
      return true;
    }
    if (!(arg0 instanceof JiraUser)) {
      return false;
    }
    return this.getName().equals(((JiraUser) arg0).getName());
  }

}
