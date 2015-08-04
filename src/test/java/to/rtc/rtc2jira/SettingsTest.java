package to.rtc.rtc2jira;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static to.rtc.rtc2jira.Settings.RTC_WORKITEM_ID_RANGE;

import java.util.Properties;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test of {@link Settings}
 * 
 * @author Manuel
 */
public class SettingsTest {

  private Settings settings;
  private Properties properties;

  @Before
  public void setUp() throws Exception {
    properties = new Properties();
    settings = Settings.getInstance();
    settings.setProperties(properties);
  }

  @Test
  public void testWorkItemRange_WithRange_ExpectReturnWholeRange() {
    String range = "10..20";
    properties.setProperty(RTC_WORKITEM_ID_RANGE, range);

    Integer[] expectedRange = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    assertThat(settings.getRtcWorkItemRange(), containsInAnyOrder(expectedRange));
  }

  @Test
  @Ignore("not implemented yet")
  public void testWorkItemRange_Two_CommaSeparated_ExpectTwo() {
    String range = "1,3";
    properties.setProperty(RTC_WORKITEM_ID_RANGE, range);

    Integer[] expectedRange = {1, 3};
    assertThat(settings.getRtcWorkItemRange(), containsInAnyOrder(expectedRange));
  }

  @Test
  @Ignore("not implemented yet")
  public void testWorkItemRange_Range_And_CommaSeparated_Mixed() {
    String range = "1,34..36";
    properties.setProperty(RTC_WORKITEM_ID_RANGE, range);

    Integer[] expectedRange = {1, 34, 35, 36};
    assertThat(settings.getRtcWorkItemRange(), containsInAnyOrder(expectedRange));
  }
}
