package to.rtc.rtc2jira;

import static org.junit.Assert.*;

import java.util.List;

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

import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.exporter.github.GitHubExporter;
import to.rtc.rtc2jira.exporter.jira.JiraExporter;
import to.rtc.rtc2jira.storage.StorageEngine;

import com.orientechnologies.orient.core.record.impl.ODocument;

public class ExportManagerTest {

  @Mocked
  private Settings settingsMock;
  @Mocked 
  private Exporter exporter;
  @Mocked
  private RepositoryService _service;

  private StorageEngine engine;

  @Rule
  public TestDatabaseRule testDbRule = new TestDatabaseRule();


  @Before
  public void setUp() throws Exception {
    engine = testDbRule.getEngine();
  }
      
  @Test
  public void testExport_EmptyDB_ExpectNoExport(@Mocked IssueService issueServiceMock)
      throws Exception {
    new Expectations() {
      {
        exporter.isConfigured();
        result = true;
      }
    };
    ExportManager exportManager = new ExportManager();
    exportManager.addExporters(exporter);
    exportManager.export(settingsMock, engine);
    new Verifications() {
      {
        exporter.initialize(settingsMock, engine);
        times = 1;

        issueServiceMock.createIssue(withInstanceOf(IRepositoryIdProvider.class),
            withInstanceOf(Issue.class));
        times = 0;
        
        exporter.createOrUpdateItem(withInstanceOf(ODocument.class));
        times = 0;
      }
    };
  }

  @Test
  public void testExport_WithDBEntries_ExpectExport(@Mocked Repository repoMock, @Mocked IssueService issueServiceMock)
      throws Exception {
    new Expectations() {
      {
        exporter.isConfigured();
        result = true;        
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
        exporter.initialize(settingsMock, engine);
        times = 1;
        
        exporter.createOrUpdateItem(withInstanceOf(ODocument.class));
        times = 2;
      }
    };
  }

  private void createWorkItem(int id) {
    ODocument doc = new ODocument("WorkItem");
    doc.field("ID", id);
    doc.save();
  }  
    
  
  @Test
  public void testAddExporters() throws Exception {
    ExportManager exportManager = new ExportManager();
    GitHubExporter gitHubExporter = new GitHubExporter();
    JiraExporter jiraExporter = new JiraExporter();
    exportManager.addExporters(gitHubExporter, jiraExporter);
    List<Exporter> exporters = exportManager.getExporters();
    assertSame(gitHubExporter, exporters.get(0));
    assertSame(jiraExporter, exporters.get(1));    
  }

}
