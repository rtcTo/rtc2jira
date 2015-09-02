package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class JiraDateStringAdapterTest {

  @Test
  public void testUnmarshalString() throws Exception {
    String literal = "2017-06-21T13:45:09.768+0300";
    Date date = new JiraDateStringAdapter().unmarshal(literal);
    Assert.assertEquals(1498041909, date.toInstant().getEpochSecond());
  }

  @Test
  public void testMarshalDate() throws Exception {
    String literal = "2017-06-21T13:45:09.768+0300";
    JiraDateStringAdapter jiraDateStringAdapter = new JiraDateStringAdapter();
    Date date = jiraDateStringAdapter.unmarshal(literal);
    String marshalled = jiraDateStringAdapter.marshal(date);
    Assert.assertEquals("2017-06-21T12:45:09.768+02:00", marshalled);
  }

}
