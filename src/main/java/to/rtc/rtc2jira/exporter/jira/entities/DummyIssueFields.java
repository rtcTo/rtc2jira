package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class DummyIssueFields {

  private IssueType issuetype;
  private ProjectOverview project;
  private String summary;
  private String description;

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
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("issuetype", getIssuetype());
    result.put("project", getProject());
    result.put("summary", getSummary());
    result.put("description", getDescription());
    return result;
  }
}
