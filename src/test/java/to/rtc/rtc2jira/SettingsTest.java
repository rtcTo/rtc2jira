package to.rtc.rtc2jira;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.Properties;

import org.junit.Test;

public class SettingsTest {

  @Test
  public void testExtractingSingleRange() {
    Properties props = new Properties();
    props.setProperty(Settings.RTC_WORKITEM_ID_RANGE, "12..14");
    Settings settings = new Settings(props);
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
    Settings settings = new Settings(props);
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
