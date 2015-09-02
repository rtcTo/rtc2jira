package to.rtc.rtc2jira.exporter.jira.entities;

public class IssuePriority extends NamedEntity {


  public static IssuePriority createWithId(String id) {
    return new IssuePriority(id, null);
  }

  public static IssuePriority createWithName(String name) {
    return new IssuePriority(null, name);
  }

  public IssuePriority() {
    super();
  }

  private IssuePriority(String id, String name) {
    super(id, name);
  }


  @Override
  public String getPath() {
    return "/priority";
  }

  @Override
  public String getSelfPath() {
    return getPath() + "/" + getId();
  }
}
