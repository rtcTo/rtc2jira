package to.rtc.rtc2jira.exporter.jira;

import static mockit.Deencapsulation.invoke;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.TestDatabaseRule;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueFields;
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
  JiraPersistence persistence;
  @Mocked
  JiraRestAccess restAccess;
  @Mocked
  Map<String, List<IssueType>> existingIssueTypes;

  @Rule
  public TestDatabaseRule testDbRule = new TestDatabaseRule();

  @Test
  public void testInitialize(@Mocked ClientResponse clientResponse, @Mocked StorageEngine store)
      throws Exception {
    new Expectations() {
      {
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
  public void testIsConfigured_serverOK(@Mocked ClientResponse clientResponse,
      @Mocked StorageEngine store) throws Exception {
    new Expectations() {
      {
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

  @Test
  public void testIsConfigured_serverNotOK(@Mocked ClientResponse clientResponse,
      @Mocked StorageEngine store) throws Exception {
    new Expectations() {
      {
        clientResponse.getStatus();
        result = Status.NOT_FOUND.getStatusCode();
      }
    };

    JiraExporter jiraExporter = new JiraExporter();
    jiraExporter.initialize(settings, store);
    boolean isConfigured = jiraExporter.isConfigured();
    assertNotEquals(true, isConfigured);
  }


  public void testCreateOrUpdateItem(@Mocked ClientResponse clientResponse,
      @Mocked StorageEngine store, @Mocked Repository repoMock, @Mocked RepositoryService service,
      @Mocked IssueService issueServiceMock, @Mocked ODocument workItem) throws Exception {

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
  public void testCreateOrUpdateItem_forceCreate(@Mocked ODocument workItem,
      @Mocked StorageEngine store, @Mocked StorageQuery storageQuery,
      @Mocked Optional<Project> projectOptional) throws Exception {

    JiraExporter jiraExporter = new JiraExporter();

    new Expectations(jiraExporter) {
      {
        StorageQuery.getField(workItem, FieldNames.JIRA_ID_LINK, "");
        result = "123";

        StorageQuery.getField(workItem, FieldNames.JIRA_KEY_LINK, "");
        result = "WOR_123";

        invoke(jiraExporter, "forceCreate");
        result = true;
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
  public void testCreateOrUpdateItem_itemIdEmpty(@Mocked ODocument workItem,
      @Mocked StorageEngine store, @Mocked StorageQuery storageQuery,
      @Mocked Optional<Project> projectOptional) throws Exception {

    JiraExporter jiraExporter = new JiraExporter();

    new Expectations(jiraExporter) {
      {
        StorageQuery.getField(workItem, FieldNames.JIRA_ID_LINK, "");
        result = "";

        StorageQuery.getField(workItem, FieldNames.JIRA_KEY_LINK, "");
        result = "";

        invoke(jiraExporter, "forceCreate");
        result = false;
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

  @Ignore
  public void testCreateItem() throws Exception {

    final StorageEngine engine = testDbRule.getEngine();

    ODocument workItem = new ODocument();
    workItem.field(FieldNames.DESCRIPTION, "The description");

    IssueFields fields = new IssueFields();
    fields.setDescription("The description");

    Issue issue = new Issue();
    issue.setId("123");
    issue.setKey("theKey");
    issue.setFields(fields);

    JiraExporter jiraExporter = new JiraExporter();
    new Expectations(jiraExporter, issue) {
      {
        Issue.createFromWorkItem(workItem);
        result = issue;
      }
    };
    jiraExporter.initialize(settings, engine);

    assertEquals("", StorageQuery.getField(workItem, FieldNames.JIRA_ID_LINK, ""));
    assertNull(workItem.field(FieldNames.JIRA_KEY_LINK));
    assertNull(workItem.field(FieldNames.JIRA_EXPORT_TIMESTAMP));
    assertNull(workItem.field(FieldNames.JIRA_EXPORT_TIMESTAMP));

    jiraExporter.createItem(workItem);

    assertEquals("123", StorageQuery.getField(workItem, FieldNames.JIRA_ID_LINK, ""));
    assertEquals("theKey", workItem.field(FieldNames.JIRA_KEY_LINK));
    assertNotNull(workItem.field(FieldNames.JIRA_EXPORT_TIMESTAMP));
    assertEquals("The description", workItem.field(FieldNames.DESCRIPTION));

    new Verifications() {
      {
        Issue.createFromWorkItem(workItem);
        jiraExporter.storeReference(withInstanceOf(Issue.class), workItem);
        jiraExporter.storeTimestampOfLastExport(workItem);
      }
    };
  }

  @Test
  public void testCreateOrUpdateItem_notForceCreate(@Mocked ODocument workItem,
      @Mocked StorageEngine store, @Mocked StorageQuery storageQuery) throws Exception {

    JiraExporter jiraExporter = new JiraExporter();

    new Expectations(jiraExporter) {
      {
        StorageQuery.getField(workItem, FieldNames.JIRA_ID_LINK, "");
        result = "123";

        settings.isForceCreate();
        result = false;

        StorageQuery.getField(workItem, FieldNames.MODIFIED, withInstanceOf(Date.class));
        result = Date.from(Instant.now());
        StorageQuery.getField(workItem, FieldNames.JIRA_EXPORT_TIMESTAMP,
            withInstanceOf(Date.class));
        result = new Date(0);

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
