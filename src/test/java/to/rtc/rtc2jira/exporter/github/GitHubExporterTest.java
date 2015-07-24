package to.rtc.rtc2jira.exporter.github;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.TestDatabaseRule;
import to.rtc.rtc2jira.storage.StorageEngine;

import com.orientechnologies.orient.core.record.impl.ODocument;

public class GitHubExporterTest {

  private GitHubExporter exporter;

  @Mocked
  private Settings settingsMock;

  @Mocked
  private RepositoryService _service;

  private StorageEngine engine;

  @Rule
  public TestDatabaseRule testDbRule = new TestDatabaseRule();


  @Before
  public void setUp() throws Exception {
    engine = testDbRule.getEngine();
    exporter = new GitHubExporter();
    exporter.initialize(settingsMock, engine);
  }

  @Test
  public void testIsConfigured_WithWrongCredentials_ExpectIsNotConfigured() throws Exception {
    new Expectations() {
      {
        settingsMock.hasGithubProperties();
        result = true;
        _service.getRepository(anyString, anyString);
        result = new IOException("Wrong credentials");
      }
    };
    assertFalse(exporter.isConfigured());
  }

  @Test
  public void testIsConfigured_WithValidCredentials_ExpectIsConfigured(@Mocked Repository repoMock)
      throws IOException {
    new Expectations() {
      {
        settingsMock.hasGithubProperties();
        result = true;
        _service.getRepository(anyString, anyString);
        result = repoMock;
      }
    };
    assertTrue(exporter.isConfigured());
  }

  @Test
  public void testExport_WithoutEmptyDB_ExpectNoExport(@Mocked IssueService issueServiceMock)
      throws Exception {
    exporter.export();
    new Verifications() {
      {
        issueServiceMock.createIssue(withInstanceOf(IRepositoryIdProvider.class),
            withInstanceOf(Issue.class));
        times = 0;
      }
    };
  }

  @Test
  public void testExport_WithDBEntries_ExpectExport(@Mocked IssueService issueServiceMock)
      throws Exception {
    engine.withDB(db -> {
      createWorkItem(123);
      createWorkItem(324);
    });
    exporter.export();

    new Verifications() {
      {
        issueServiceMock.createIssue(null, withInstanceOf(Issue.class));
        times = 2;
      }
    };
  }


  private void createWorkItem(int id) {
    ODocument doc = new ODocument("WorkItem");
    doc.field("ID", id);
    doc.save();
  }
}
