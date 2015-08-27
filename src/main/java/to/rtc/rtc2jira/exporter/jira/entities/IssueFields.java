package to.rtc.rtc2jira.exporter.jira.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueFields {

  private IssueType issuetype;
  private ProjectOverview project;
  private String summary;
  private String description;
  private Date due;
  private IssuePriority priority;
  private IssueCommentContainer comment;

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

  public Map<String, Object> toMap() {
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("issuetype", getIssuetype());
    result.put("project", getProject());
    result.put("summary", getSummary());
    result.put("description", getDescription());
    if (getDueDate() != null) {
      result.put("duedate", DATE_FORMAT.format(getDueDate()));
    }
    result.put("priority", getPriority());
    return result;
  }

  public Date getDueDate() {
    return due;
  }

  public void setDueDate(Date dueDate) {
    this.due = dueDate;
  }

  public IssuePriority getPriority() {
    return priority;
  }

  public void setPriority(IssuePriority priorty) {
    this.priority = priorty;
  }

  public IssueCommentContainer getComment() {
    return comment;
  }

  public void setComment(IssueCommentContainer comment) {
    this.comment = comment;
  }


}
