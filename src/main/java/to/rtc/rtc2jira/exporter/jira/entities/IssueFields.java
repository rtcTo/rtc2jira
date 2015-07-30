package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueFields {

  private IssueType issuetype;
  private ProjectOverview project;

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

}
