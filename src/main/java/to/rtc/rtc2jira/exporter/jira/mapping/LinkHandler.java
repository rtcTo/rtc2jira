package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.HashMap;
import java.util.Map;

import to.rtc.rtc2jira.exporter.jira.JiraRestAccess;
import to.rtc.rtc2jira.exporter.jira.entities.AddIssueLink;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueFields;
import to.rtc.rtc2jira.exporter.jira.entities.IssueLink;
import to.rtc.rtc2jira.exporter.jira.entities.IssueSearch;
import to.rtc.rtc2jira.exporter.jira.entities.IssueSearch.IssueSearchResult;
import to.rtc.rtc2jira.exporter.jira.entities.IssueType;
import to.rtc.rtc2jira.exporter.jira.entities.Project;

import com.sun.jersey.api.client.ClientResponse;

public class LinkHandler {
  Map<String, Issue> alreadyExistingNames;
  JiraRestAccess access;

  LinkHandler(JiraRestAccess access) {
    this.access = access;
    this.alreadyExistingNames = new HashMap<String, Issue>();
  }


  protected Issue createIssue(String summary, IssueType issueType, Project project) {
    Issue issue = new Issue();
    IssueFields fields = issue.getFields();
    fields.setProject(project);
    fields.setIssuetype(issueType);
    fields.setSummary(summary);
    ClientResponse post = access.post("/issue", issue);
    if (post.getStatus() == 201) {
      Issue newIssueLink = post.getEntity(Issue.class);
      String key = newIssueLink.getKey();
      String id = key.split("-")[1];
      issue.setId(id);
      issue.setKey(key);
      issue.setSelf(newIssueLink.getSelf());
    } else {
      issue = null;
    }
    return issue;
  }


  protected Issue getIssueBySummary(String summary, IssueType issueType, Project project) {
    Issue result = alreadyExistingNames.get(summary);
    if (result == null) {
      // may exist, but was not yet cached
      IssueSearch issueSearch = IssueSearch.INSTANCE;
      IssueSearchResult searchResult =
          issueSearch.run("issuetype=" + issueType.getId() + " AND summary~'" + summary + "'");
      if (searchResult.getTotal() > 0) {
        result = searchResult.getIssues().get(0);
      } else {
        // definitely does not exist - create
        result = createIssue(summary, issueType, project);
        if (result != null) {
          alreadyExistingNames.put(summary, result);
        }
      }
    }
    return result;
  }


  public boolean removeLink(IssueLink issueLink) {
    ClientResponse post = access.delete(issueLink.getSelfPath());
    if (post.getStatus() == 204) {
      return true;
    } else {
      // LOG PROBLEM
      return false;
    }
  }


  public AddIssueLink linkIssues(AddIssueLink addIssueLink) {
    ClientResponse post = access.post("/issueLink", addIssueLink);
    if (post.getStatus() == 201) {
      addIssueLink.getInwardIssue().getFields().getIssuelinks().add(addIssueLink);
      return addIssueLink;
    } else {
      // LOG PROBLEM
      return null;
    }
  }

}
