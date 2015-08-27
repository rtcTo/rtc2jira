package to.rtc.rtc2jira.exporter.jira;

import static mockit.Deencapsulation.invoke;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.junit.Rule;
import org.junit.Test;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.TestDatabaseRule;
import to.rtc.rtc2jira.exporter.jira.entities.IssueType;
import to.rtc.rtc2jira.exporter.jira.entities.Project;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;
import to.rtc.rtc2jira.storage.StorageQuery;

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

    JiraExporter jiraExporter = new JiraExporter();
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

    JiraExporter jiraExporter = new JiraExporter();
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

  @Test(expected = RuntimeException.class)
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

    JiraExporter jiraExporter = new JiraExporter();
    assertTrue(jiraExporter.isConfigured());
    jiraExporter.initialize(settings, store);
  }


  public void testCreateOrUpdateItem_Update(@Mocked ClientResponse clientResponse, @Mocked StorageEngine store,
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

    JiraExporter jiraExporter = new JiraExporter();
    jiraExporter.initialize(settings, store);
    jiraExporter.createOrUpdateItem(workItem);

    new Verifications() {
      {
      }
    };
  }


  @Test
  public void testCreateOrUpdateItem_CreateWithCorrectId(@Mocked ODocument workItem, @Mocked StorageEngine store,
      @Mocked StorageQuery storageQuery, @Mocked Optional<Project> projectOptional) throws Exception {

    JiraExporter jiraExporter = new JiraExporter();

    new Expectations(jiraExporter) {
      {
        settings.hasJiraProperties();
        result = true;

        StorageQuery.getField(workItem, FieldNames.JIRA_ID_LINK, "");
        result = "";
      }
    };


    jiraExporter.initialize(settings, store);
    jiraExporter.createOrUpdateItem(workItem);

    new Verifications() {
      {
        invoke(jiraExporter, "createItem", workItem);
      }
    };
  }


  @Test
  public void testCreateOrUpdateItem(@Mocked ODocument workItem, @Mocked StorageEngine store,
      @Mocked StorageQuery storageQuery) throws Exception {

    JiraExporter jiraExporter = new JiraExporter();

    new Expectations(jiraExporter) {
      {
        settings.hasJiraProperties();
        result = true;

        StorageQuery.getField(workItem, FieldNames.JIRA_ID_LINK, "");
        result = "123";

        StorageQuery.getField(workItem, FieldNames.MODIFIED, withInstanceOf(Date.class));
        result = Date.from(Instant.now());
        StorageQuery.getField(workItem, FieldNames.JIRA_EXPORT_TIMESTAMP, withInstanceOf(Date.class));
        result = new Date(0);

        invoke(jiraExporter, "createIssueInJira");

        invoke(jiraExporter, "updateItem", workItem);
      }
    };

    jiraExporter.initialize(settings, store);
    jiraExporter.createOrUpdateItem(workItem);

    new Verifications() {
      {
        invoke(jiraExporter, "updateItem", workItem);
      }
    };
  }
}
