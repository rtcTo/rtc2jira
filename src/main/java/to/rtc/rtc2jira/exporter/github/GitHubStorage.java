package to.rtc.rtc2jira.exporter.github;

import java.util.Optional;

import org.eclipse.egit.github.core.Issue;

import com.orientechnologies.orient.core.record.impl.ODocument;

import to.rtc.rtc2jira.storage.Field;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * Used for storing/retrieving GitHub related stuff into/out of database
 * 
 * @author Manuel
 */
final class GitHubStorage {

  private StorageEngine store;

  GitHubStorage(StorageEngine engine) {
    this.store = engine;
  }

  final void storeLinkToIssueInWorkItem(Optional<Issue> optionalIssue, ODocument workItem) {
    optionalIssue.ifPresent(issue -> {
      int newIssueGithubNumber = issue.getNumber();
      store.setField(workItem, Field.of(FieldNames.GITHUB_WORKITEM_LINK, newIssueGithubNumber));
    });
  }

  StorageEngine getStorage() {
    return store;
  }

}
