package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import to.rtc.rtc2jira.exporter.jira.JiraExporter;
import to.rtc.rtc2jira.exporter.jira.JiraRestAccess;

public class IssueSearch {
  public static final IssueSearch INSTANCE;
  private JiraRestAccess restAccess;

  static {
    INSTANCE = new IssueSearch(JiraExporter.INSTANCE.getRestAccess());
  }

  private IssueSearch(JiraRestAccess access) {
    restAccess = access;
  }

  public IssueSearchResult run(String jql) {
    IssueSearchResult issueSearchResult = restAccess.get("/search?jql=" + jql, IssueSearchResult.class);
    return issueSearchResult;
  }


  @XmlRootElement
  public static class IssueSearchResult {

    int startAt;
    int maxResults;
    int total;
    List<Issue> issues;

    public int getStartAt() {
      return startAt;
    }

    public void setStartAt(int startAt) {
      this.startAt = startAt;
    }

    public int getMaxResults() {
      return maxResults;
    }

    public void setMaxResults(int maxResults) {
      this.maxResults = maxResults;
    }

    public int getTotal() {
      return total;
    }

    public void setTotal(int total) {
      this.total = total;
    }

    public List<Issue> getIssues() {
      return issues;
    }

    public void setIssues(List<Issue> issues) {
      this.issues = issues;
    }


  }


}
