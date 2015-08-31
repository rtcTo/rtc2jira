package to.rtc.rtc2jira;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static to.rtc.rtc2jira.Settings.RTC_WORKITEM_ID_RANGE;

import java.util.Properties;

import org.junit.Before;
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
    assertThat(settings.getRtcWorkItemRange(), contains(expectedRange));
  }

  @Test
  public void testWorkItemRange_Two_CommaSeparated_ExpectTwo() {
    String range = "1,3";
    properties.setProperty(RTC_WORKITEM_ID_RANGE, range);

    Integer[] expectedRange = {1, 3};
    assertThat(settings.getRtcWorkItemRange(), contains(expectedRange));
  }

  @Test
  public void testWorkItemRange_Range_And_CommaSeparated_Mixed() {
    String range = "1,34..36";
    properties.setProperty(RTC_WORKITEM_ID_RANGE, range);

    Integer[] expectedRange = {1, 34, 35, 36};
    assertThat(settings.getRtcWorkItemRange(), contains(expectedRange));
  }

  @Test
  public void testWorkItemRange_Range_And_CommaSeparated_Mixed_WithSpaces() {
    String range = "1 , 34..36 ";
    properties.setProperty(RTC_WORKITEM_ID_RANGE, range);

    Integer[] expectedRange = {1, 34, 35, 36};
    assertThat(settings.getRtcWorkItemRange(), contains(expectedRange));
  }

  @Test
  public void testExtractingSingleRange() {
    Properties props = new Properties();
    props.setProperty(Settings.RTC_WORKITEM_ID_RANGE, "12..14");
    Settings.getInstance().setProperties(props);

    Integer[] expectedRange = {12, 13, 14};
    assertThat(settings.getRtcWorkItemRange(), contains(expectedRange));
  }

  @Test
  public void testExtractingMultipleRanges() {
    Properties props = new Properties();
    props.setProperty(Settings.RTC_WORKITEM_ID_RANGE, "12..14,20..22");
    Settings.getInstance().setProperties(props);

    Integer[] expectedRange = {12, 13, 14, 20, 21, 22};
    assertThat(settings.getRtcWorkItemRange(), contains(expectedRange));
  }

  @Test
  public void testTrimming() throws Exception {
    Properties props = new Properties();
    props.setProperty("proxy.host", "   my.proxy.host   ");
    Settings.getInstance().setProperties(props);
    assertThat(settings.getProxyHost(), is("my.proxy.host"));
  }

}
