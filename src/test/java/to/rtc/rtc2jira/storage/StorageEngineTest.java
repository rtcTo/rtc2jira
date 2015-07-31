package to.rtc.rtc2jira.storage;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.orientechnologies.orient.core.record.impl.ODocument;

import to.rtc.rtc2jira.TestDatabaseRule;

public class StorageEngineTest {

  @Rule
  public TestDatabaseRule testDbRule = new TestDatabaseRule();
  private StorageEngine storage;

  @Before
  public void setUp() throws Exception {
    storage = testDbRule.getEngine();
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
    storage.withDB(db -> {
      ODocument doc = new ODocument("WorkItem");
      doc.field("ID", id);
      doc.save();
      reference.set(doc);
    });
    return reference.get();
  }
}
