package to.rtc.rtc2jira.exporter.github;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static to.rtc.rtc2jira.exporter.github.GitHubStorage.GITHUB_WORKITEM_LINK;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.egit.github.core.Issue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import to.rtc.rtc2jira.TestDatabaseRule;
import to.rtc.rtc2jira.storage.StorageEngine;

import com.orientechnologies.orient.core.record.impl.ODocument;

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
    ODocument workItem = createWorkItem(1);

    storage.storeLinkToIssueInWorkItem(Optional.ofNullable(issue), workItem);

    boolean isStored = workItem.field(GITHUB_WORKITEM_LINK) != null;
    assertFalse(isStored);
  }

  @Test
  public void testStoreIssue_IssueIsGiven_ExpectLinkToIssueIsStored() {
    int expectedIssueNumber = 1337;
    Issue issue = new Issue().setNumber(expectedIssueNumber);
    ODocument workitem = createWorkItem(2);

    storage.storeLinkToIssueInWorkItem(Optional.ofNullable(issue), workitem);

    Object link = workitem.field(GITHUB_WORKITEM_LINK);
    assertEquals(1337, link);
  }

  @Test
  public void testGetWorkItems_NoWorkItems_ShouldReturnEmptyCollection() {
    assertEquals(0, storage.getRTCWorkItems().size());
  }

  @Test
  public void testGetWorkItems_TwoWorkItems_ShouldReturnThem() {
    List<ODocument> createdWorkItemsSorted = Stream.of(createWorkItem(1), createWorkItem(2)) //
        .sorted().collect(Collectors.toList());

    List<ODocument> storedWorkItems = storage.getRTCWorkItems();

    assertEquals(2, storedWorkItems.size());
    List<ODocument> storedWorkItemsSorted = storedWorkItems.stream() //
        .sorted().collect(Collectors.toList());
    assertEquals(createdWorkItemsSorted, storedWorkItemsSorted);
  }

  private ODocument createWorkItem(int id) {
    final AtomicReference<ODocument> reference = new AtomicReference<>();
    storageEngine.withDB(db -> {
      ODocument doc = new ODocument("WorkItem");
      doc.field("ID", id);
      doc.save();
      reference.set(doc);
    });
    return reference.get();
  }
}
