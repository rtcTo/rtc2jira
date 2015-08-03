package to.rtc.rtc2jira.storage;

import static org.junit.Assert.assertEquals;
import static to.rtc.rtc2jira.storage.Field.of;

import org.junit.Test;

public class FieldTest {

  @Test
  public void testNameAndValueBothSet_ShouldRemainSame() throws Exception {
    String name = "aName";
    String value = "aValue";
    Field field = of(name, value);

    assertEquals(name, field.getName());
    assertEquals(value, field.getValue());
  }

}
