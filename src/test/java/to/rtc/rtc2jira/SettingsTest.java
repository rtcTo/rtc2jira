package to.rtc.rtc2jira;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static to.rtc.rtc2jira.Settings.RTC_WORKITEM_ID_RANGE;

import java.util.Iterator;
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

  @Test
  public void testExtractingSingleRange() {
    Properties props = new Properties();
    props.setProperty(Settings.RTC_WORKITEM_ID_RANGE, "12..14");
    Settings.getInstance().setProperties(props);
    Iterable<Integer> range = settings.getRtcWorkItemRange();
    Iterator<Integer> iterator = range.iterator();
    assertThat(iterator.next(), is(12));
    assertThat(iterator.next(), is(13));
    assertThat(iterator.next(), is(14));
    assertFalse(iterator.hasNext());
  }

  @Test
  public void testExtractingMultipleRanges() {
    Properties props = new Properties();
    props.setProperty(Settings.RTC_WORKITEM_ID_RANGE, "12..14,20..22");
    Settings.getInstance().setProperties(props);
    Iterable<Integer> range = settings.getRtcWorkItemRange();
    Iterator<Integer> iterator = range.iterator();
    assertThat(iterator.next(), is(12));
    assertThat(iterator.next(), is(13));
    assertThat(iterator.next(), is(14));
    assertThat(iterator.next(), is(20));
    assertThat(iterator.next(), is(21));
    assertThat(iterator.next(), is(22));
    assertFalse(iterator.hasNext());
  }

}
