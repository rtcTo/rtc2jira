package to.rtc.rtc2jira.exporter.github;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.egit.github.core.Issue;

import to.rtc.rtc2jira.storage.StorageEngine;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

/**
 * Used for storing/retrieving GitHub related stuff into/out of database
 * 
 * @author Manuel
 */
public final class GitHubStorage {

  static final String GITHUB_WORKITEM_LINK = "githubissuenumber";

  private StorageEngine store;

  GitHubStorage(StorageEngine engine) {
    this.store = engine;
  }

  final void storeLinkToIssueInWorkItem(Optional<Issue> optionalIssue, ODocument workItem) {
    optionalIssue.ifPresent(issue -> {
      store.withDB(db -> {
        int newIssueGithubNumber = issue.getNumber();
        workItem.field(GITHUB_WORKITEM_LINK, newIssueGithubNumber);
        workItem.save();
      });
    });
  }

  final List<ODocument> getRTCWorkItems() {
    final List<ODocument> result = new ArrayList<>();
    store.withDB(db -> {
      OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<ODocument>("select * from WorkItem");
      List<ODocument> queryResults = db.query(query);
      result.addAll(queryResults);
    });
    return result;
  }

}
