package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonView;

@XmlRootElement
public class IssueComment extends BaseEntity {

  private String body;

  @JsonView(IssueView.Filtered.class)
  private Issue issue;

  public static IssueComment createWithId(Issue issue, String id) {
    return new IssueComment(issue, id, null);
  }

  public static IssueComment createWithBody(Issue issue, String body) {
    return new IssueComment(issue, null, body);
  }

  public static IssueComment createWithIdAndBody(Issue issue, String id, String body) {
    return new IssueComment(issue, id, body);
  }

  public IssueComment() {
    super();
  }

  private IssueComment(Issue issue, String id, String body) {
    super(id);
    this.issue = issue;
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
    return "/issue/" + getIssue().getKey() + "/comment";
  }

  public Issue getIssue() {
    return issue;
  }

  public void setIssue(Issue issue) {
    this.issue = issue;
  }


}
