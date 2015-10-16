package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlRootElement;

import to.rtc.rtc2jira.exporter.jira.JiraExporter;
import to.rtc.rtc2jira.exporter.jira.JiraRestAccess;

import com.sun.jersey.api.client.ClientResponse;

public class IssueSearch {
  private static final Logger LOGGER = Logger.getLogger(IssueSearch.class.getName());

  public static final IssueSearch INSTANCE;
  private JiraRestAccess restAccess;

  static {
    INSTANCE = new IssueSearch(JiraExporter.INSTANCE.getRestAccess());
  }

  private IssueSearch(JiraRestAccess access) {
    restAccess = access;
  }

  public IssueSearchResult run(String jql) {
    IssueSearchResult issueSearchResult = new IssueSearchResult();
    issueSearchResult.issues = Collections.emptyList();
    issueSearchResult.total = 0;
    issueSearchResult.startAt = 0;
    issueSearchResult.maxResults = 0;
    ClientResponse clientResponse = restAccess.get("/search?jql=" + jql);
    if (clientResponse.getStatus() == 200) {
      issueSearchResult = clientResponse.getEntity(IssueSearchResult.class);
    } else {
      String responseEntity = clientResponse.getEntity(String.class);
      LOGGER.warning("JQL search failed. Query string: '" + jql + "'. Response entity: " + responseEntity);
    }
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
