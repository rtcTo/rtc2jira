package to.rtc.rtc2jira.exporter.github;

import java.io.IOException;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import to.rtc.rtc2jira.ExportManager;
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

  @ClassRule
  public static TestDatabaseRule testDbRule = new TestDatabaseRule();


  @Before
  public void setUp() throws Exception {
    engine = testDbRule.getEngine();
    exporter = new GitHubExporter();
    new NonStrictExpectations() {
      {
        settingsMock.hasGithubProperties();
        result = true;
      }
    };
  }

  @Test(expected = IOException.class)
  public void testInitialize_WithWrongCredentials_ExpectException() throws Exception {
    new Expectations() {
      {
        _service.getRepository(anyString, anyString);
        result = new IOException("Wrong credentials");
      }
    };
    exporter.initialize(settingsMock, engine);
  }

  @Test
  public void testInitialize_WithValidCredentials_ExpectIsConfigured(@Mocked Repository repoMock) throws IOException {
    new Expectations() {
      {
        _service.getRepository(anyString, anyString);
        result = repoMock;
      }
    };
    exporter.initialize(settingsMock, engine);
  }

  @Test
  public void testExport_WithoutEmptyDB_ExpectNoExport(@Mocked IssueService issueServiceMock) throws Exception {
    ExportManager exportManager = new ExportManager();
    exportManager.addExporters(exporter);
    exportManager.export(settingsMock, engine);
    new Verifications() {
      {
        issueServiceMock.createIssue(withInstanceOf(IRepositoryIdProvider.class), withInstanceOf(Issue.class));
        times = 0;
      }
    };
  }

  @Test
  public void testExport_WithDBEntries_ExpectExport(@Mocked Repository repoMock, @Mocked IssueService issueServiceMock)
      throws Exception {
    new Expectations() {
      {
        settingsMock.hasGithubProperties();
        result = true;
        _service.getRepository(anyString, anyString);
        result = repoMock;
      }
    };
    engine.withDB(db -> {
      createWorkItem(123);
      createWorkItem(324);
    });
    ExportManager exportManager = new ExportManager();
    exportManager.addExporters(exporter);
    exportManager.export(settingsMock, engine);

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
