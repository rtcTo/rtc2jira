package to.rtc.rtc2jira.exporter.jira.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.map.annotate.JsonView;

@XmlRootElement
public class IssueComment extends BaseEntity {

  private String body;
  private JiraUser author;
  private Issue issue;
  private Date created;

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
    String author = getAuthor().getDisplayName();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    return "<RTC: " + author + " " + dateFormat.format(getCreated()) + ">\n\n" + body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String getPath() {
    return "/issue/" + getIssue().getKey() + "/comment";
  }

  @Override
  public String getSelfPath() {
    return getPath() + "/" + getId();
  }


  @JsonView(IssueView.Filtered.class)
  public Issue getIssue() {
    return issue;
  }

  public void setIssue(Issue issue) {
    this.issue = issue;
  }

  public JiraUser getAuthor() {
    return author;
  }

  public void setAuthor(JiraUser author) {
    this.author = author;
  }

  @XmlJavaTypeAdapter(JiraDateStringAdapter.class)
  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }


}
