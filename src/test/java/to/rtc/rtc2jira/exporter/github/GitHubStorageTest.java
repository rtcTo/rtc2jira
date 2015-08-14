package to.rtc.rtc2jira.exporter.github;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static to.rtc.rtc2jira.storage.TestWorkItemCreator.createWorkItem;

import java.util.Optional;

import org.eclipse.egit.github.core.Issue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.orientechnologies.orient.core.record.impl.ODocument;

import to.rtc.rtc2jira.TestDatabaseRule;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * Test of {@link GitHubStorage}
 * 
 * @author Manuel
 */
public class GitHubStorageTest {

  @Rule
  public TestDatabaseRule testDbRule = new TestDatabaseRule();
  private GitHubStorage storage;
  private StorageEngine storageEngine;

  @Before
  public void setUp() throws Exception {
    storageEngine = testDbRule.getEngine();
    storage = new GitHubStorage(storageEngine);
  }

  @Test
  public void testStoreIssue_IssueIsNull_ExpectNoExceptionAndNoStorage() {
    Issue issue = null;
    ODocument workItem = createWorkItem(1, storageEngine);

    storage.storeLinkToIssueInWorkItem(Optional.ofNullable(issue), workItem);

    boolean isStored = workItem.field(FieldNames.GITHUB_WORKITEM_LINK) != null;
    assertFalse(isStored);
  }

  @Test
  public void testStoreIssue_IssueIsGiven_ExpectLinkToIssueIsStored() {
    int expectedIssueNumber = 1337;
    Issue issue = new Issue().setNumber(expectedIssueNumber);
    ODocument workitem = createWorkItem(2, storageEngine);

    storage.storeLinkToIssueInWorkItem(Optional.ofNullable(issue), workitem);

    Object link = workitem.field(FieldNames.GITHUB_WORKITEM_LINK);
    assertEquals(1337, link);
  }
}
