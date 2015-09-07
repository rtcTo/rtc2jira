package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BulkCreateResponseEntity {

  private List<Issue> issues;

  public List<Issue> getIssues() {
    return issues;
  }

  public void setIssues(List<Issue> issues) {
    this.issues = issues;
  }


}
