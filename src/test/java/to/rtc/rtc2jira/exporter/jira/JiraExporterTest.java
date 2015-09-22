package to.rtc.rtc2jira.exporter.jira;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.TestDatabaseRule;
import to.rtc.rtc2jira.exporter.jira.entities.IssueType;
import to.rtc.rtc2jira.storage.StorageEngine;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;

public class JiraExporterTest {

  @Mocked
  Settings settings;
  @Mocked
  JiraRestAccess restAccess;
  @Mocked
  Map<String, List<IssueType>> existingIssueTypes;

  @Rule
  public TestDatabaseRule testDbRule = new TestDatabaseRule();

  @Test
  public void testInitialize(@Mocked ClientResponse clientResponse, @Mocked StorageEngine store) throws Exception {
    new Expectations() {
      {
        settings.hasJiraProperties();
        result = true;

        clientResponse.getStatus();
        result = Status.OK.getStatusCode();
      }
    };

    JiraExporter jiraExporter = JiraExporter.INSTANCE;
    jiraExporter.initialize(settings, store);
    jiraExporter.isConfigured();

    new Verifications() {
      {
        String p;
        restAccess.get(p = withCapture());
        assertEquals("/myself", p);
      }
    };

  }

  @Test
  public void testIsConfigured_serverOK(@Mocked ClientResponse clientResponse, @Mocked StorageEngine store)
      throws Exception {
    new Expectations() {
      {
        settings.hasJiraProperties();
        result = true;

        clientResponse.getStatus();
        result = Status.OK.getStatusCode();
      }
    };

    JiraExporter jiraExporter = JiraExporter.INSTANCE;
    jiraExporter.initialize(settings, store);
    boolean isConfigured = jiraExporter.isConfigured();
    assertEquals(true, isConfigured);

    new Verifications() {
      {
        String p;
        restAccess.get(p = withCapture());
        assertEquals("/myself", p);
      }
    };
  }

  @Test
  public void testIsConfigured_serverNotOK(@Mocked ClientResponse clientResponse, @Mocked StorageEngine store)
      throws Exception {
    new Expectations() {
      {
        settings.hasJiraProperties();
        result = true;

        clientResponse.getStatus();
        result = Status.NOT_FOUND.getStatusCode();
      }
    };

    JiraExporter jiraExporter = JiraExporter.INSTANCE;

    try {
      jiraExporter.initialize(settings, store);
      Assert.fail("Exception should have been thrown");
    } catch (Exception e) {
    }
    boolean isConfigured = jiraExporter.isConfigured();
    assertEquals(true, isConfigured);
  }


  public void testCreateOrUpdateItem(@Mocked ClientResponse clientResponse, @Mocked StorageEngine store,
      @Mocked Repository repoMock, @Mocked RepositoryService service, @Mocked IssueService issueServiceMock,
      @Mocked ODocument workItem) throws Exception {

    new Expectations() {
      {
        settings.hasJiraProperties();
        result = true;

        clientResponse.getStatus();
        result = Status.OK.getStatusCode();

        service.getRepository(anyString, anyString);
        result = repoMock;
      }
    };

    JiraExporter jiraExporter = JiraExporter.INSTANCE;
    jiraExporter.initialize(settings, store);
    jiraExporter.createOrUpdateItem(workItem);

    new Verifications() {
      {
      }
    };
  }



}
