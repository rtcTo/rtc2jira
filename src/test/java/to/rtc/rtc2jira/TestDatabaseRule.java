package to.rtc.rtc2jira;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.rules.TemporaryFolder;

import to.rtc.rtc2jira.storage.StorageEngine;

public final class TestDatabaseRule extends TemporaryFolder {
  private static final Logger LOGGER = Logger.getLogger(TestDatabaseRule.class.getName());

  private StorageEngine engine;

  @Override
  protected final void before() throws Throwable {
    super.before();
    engine = new StorageEngine();
    Path testDatabasePath = newFolder("test_databases", "rtc2Jira").toPath();
    String url = "plocal:" + testDatabasePath;
    engine.setConnectionUrl(url);
    LOGGER.info("Set connection url to " + url);
  }

  @Override
  protected final void after() {
    close();
    super.after();
  }

  public StorageEngine getEngine() {
    return engine;
  }

  private void close() {
    try {
      engine.close();
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Error while closing testdatabase", e);
    }
  }

}
