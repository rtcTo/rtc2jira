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
    List<ODocument> createdWorkItemsSorted = Stream.of(createWorkItem(1, storage), createWorkItem(2, storage)) //
        .sorted().collect(Collectors.toList());

    List<ODocument> storedWorkItems = StorageQuery.getRTCWorkItems(storage);

    assertEquals(2, storedWorkItems.size());
    List<ODocument> storedWorkItemsSorted = storedWorkItems.stream() //
        .sorted().collect(Collectors.toList());
    assertEquals(createdWorkItemsSorted.get(0).field(FieldNames.ID), storedWorkItemsSorted.get(0).field(FieldNames.ID));
  }

  @Test
  public void testGetField_FieldIsNotAvailable_ShouldReturnFallBackValue() {
    ODocument doc = createWorkItem(1, storage);
    String fallbackValue = "aFallBackValue";
    String fieldValue = StorageQuery.getField(doc, "anyFieldName", fallbackValue);
    assertEquals(fallbackValue, fieldValue);
  }

  @Test
  public void testGetField_FieldIsAvailable_ShouldReturnFieldValue() {
    ODocument doc = createWorkItem(1, storage);
    String fieldname = "anyFieldName";
    String expectedValue = "anyValue";
    doc.field(fieldname, expectedValue);

    String fieldValue = StorageQuery.getField(doc, fieldname, "aFallBackValue");
    assertEquals(expectedValue, fieldValue);
  }


}
