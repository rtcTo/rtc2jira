package to.rtc.rtc2jira.exporter.jira.entities;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonView;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = NON_NULL)
public class IssueFields {

  private IssueType issuetype;
  private ProjectOverview project;
  private String summary;
  private String description;
  private IssuePriority priority;
  private Date duedate;
  private IssueCommentContainer comment = new IssueCommentContainer();

  private JiraUser creator;
  private Date created;
  private List<IssueAttachment> attachment;

  public IssueType getIssuetype() {
    return issuetype;
  }

  public void setIssuetype(IssueType issuetype) {
    this.issuetype = issuetype;
  }

  public ProjectOverview getProject() {
    return project;
  }

  public void setProject(ProjectOverview project) {
    this.project = project;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public IssuePriority getPriority() {
    return priority;
  }

  public void setPriority(IssuePriority priorty) {
    this.priority = priorty;
  }

  @XmlJavaTypeAdapter(JiraDateStringAdapter.class)
  @JsonView(IssueView.Update.class)
  public Date getDuedate() {
    return duedate;
  }

  public void setDuedate(Date duedate) {
    this.duedate = duedate;
  }

  @JsonView(IssueView.Read.class)
  public IssueCommentContainer getComment() {
    return comment;
  }

  public void setComment(IssueCommentContainer comment) {
    this.comment = comment;
  }

  @JsonView(IssueView.Read.class)
  public List<IssueAttachment> getAttachment() {
    return attachment;
  }

  public void setAttachment(List<IssueAttachment> attachment) {
    this.attachment = attachment;
  }

  @JsonView(IssueView.Read.class)
  public JiraUser getCreator() {
    return creator;
  }

  public void setCreator(JiraUser creator) {
    this.creator = creator;
  }

  @JsonView(IssueView.Read.class)
  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

}
