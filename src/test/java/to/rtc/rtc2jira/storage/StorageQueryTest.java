package to.rtc.rtc2jira.storage;

import static org.junit.Assert.assertEquals;
import static to.rtc.rtc2jira.storage.TestWorkItemCreator.createWorkItem;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import to.rtc.rtc2jira.TestDatabaseRule;

import com.orientechnologies.orient.core.record.impl.ODocument;

public class StorageQueryTest {

  @Rule
  public TestDatabaseRule testDbRule = new TestDatabaseRule();
  private StorageEngine storage;

  @Before
  public void setUp() throws Exception {
    storage = testDbRule.getEngine();
  }

  @Test
  public void testGetWorkItems_NoWorkItems_ShouldReturnEmptyCollection() {
    assertEquals(0, StorageQuery.getRTCWorkItems(storage).size());
  }

  @Test
  public void testGetWorkItems_TwoWorkItems_ShouldReturnThem() {
    List<ODocument> createdWorkItemsSorted =
        Stream.of(createWorkItem(1, storage), createWorkItem(2, storage)) //
            .sorted().collect(Collectors.toList());

    List<ODocument> storedWorkItems = StorageQuery.getRTCWorkItems(storage);

    assertEquals(2, storedWorkItems.size());
    List<ODocument> storedWorkItemsSorted = storedWorkItems.stream() //
        .sorted().collect(Collectors.toList());
    assertEquals(createdWorkItemsSorted, storedWorkItemsSorted);
  }


}
