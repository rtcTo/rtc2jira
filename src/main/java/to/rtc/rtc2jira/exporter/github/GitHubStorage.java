package to.rtc.rtc2jira.exporter.github;

import static to.rtc.rtc2jira.storage.WorkItemFieldNames.GITHUB_WORKITEM_LINK;

import java.util.Optional;

import org.eclipse.egit.github.core.Issue;

import to.rtc.rtc2jira.storage.StorageEngine;

import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Used for storing/retrieving GitHub related stuff into/out of database
 * 
 * @author Manuel
 */
public final class GitHubStorage {

  private StorageEngine store;

  GitHubStorage(StorageEngine engine) {
    this.store = engine;
  }

  final void storeLinkToIssueInWorkItem(Optional<Issue> optionalIssue, ODocument workItem) {
    optionalIssue.ifPresent(issue -> {
      int newIssueGithubNumber = issue.getNumber();
      store.setField(workItem, GITHUB_WORKITEM_LINK, newIssueGithubNumber);
    });
  }

  StorageEngine getStorage() {
    return store;
  }

}
