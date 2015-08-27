package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IssueComment extends BaseEntity {

  private String body;

  public static IssueComment createWithId(Issue issue, String id) {
    return new IssueComment(issue, id, null);
  }

  public static IssueComment createWithBody(Issue issue, String body) {
    return new IssueComment(issue, null, body);
  }

  public IssueComment() {
    super();
  }

  private IssueComment(Issue issue, String id, String body) {
    super(id);
    // this.issue = issue;
    this.body = body;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String getPath() {
    return "/comment";
  }


}
