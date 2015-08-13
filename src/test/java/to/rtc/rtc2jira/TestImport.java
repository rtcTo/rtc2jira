package to.rtc.rtc2jira;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import to.rtc.rtc2jira.storage.StorageEngine;

public class TestImport {

  private StorageEngine storageEngine;

  @Before
  public void setUp() throws Exception {
    storageEngine = new StorageEngine();
  }

  @After
  public void tearDown() throws IOException {
    storageEngine.close();
  }

  @Test
  public void testRead() throws Exception {

  }
}
