package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonView;

@XmlRootElement
public class Group extends NamedEntity {

  private UsersContainer users;

  @Override
  public String getPath() {
    return "/group";
  }

  @Override
  public String getSelfPath() {
    return getPath() + "?groupname=" + getName();
  }


  @JsonView(IssueView.class)
  public String getKey() {
    return super.getKey();
  };

  @Override
  @JsonView(IssueView.Filtered.class)
  public String getId() {
    return null;
  }

  @JsonView(IssueView.Read.class)
  public UsersContainer getUsers() {
    return users;
  }

  public void setUsers(UsersContainer users) {
    this.users = users;
  }


  @XmlRootElement
  static public class UsersContainer {

    private List<JiraUser> items;

    public List<JiraUser> getItems() {
      return items;
    }

    public void setItems(List<JiraUser> items) {
      this.items = items;
    }

  }

}
