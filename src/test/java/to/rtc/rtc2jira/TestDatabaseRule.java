package to.rtc.rtc2jira;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.rules.TemporaryFolder;

import to.rtc.rtc2jira.storage.StorageEngine;

public final class TestDatabaseRule extends TemporaryFolder {

  private StorageEngine engine;

  @Override
  protected final void before() throws Throwable {
    super.before();
    engine = new StorageEngine();
    Path testDatabasePath = newFolder("test_databases", "rtc2Jira").toPath();
    String url = "plocal:" + testDatabasePath;
    engine.setConnectionUrl(url);
    System.out.println("Set connection url to " + url);
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
      System.err.println("Error while closing testdatabase");
      e.printStackTrace();
    }
  }

}
