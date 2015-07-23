package to.rtc.rtc2jira.exporter.github;

import static to.rtc.rtc2jira.storage.WorkItemConstants.DESCRIPTION;
import static to.rtc.rtc2jira.storage.WorkItemConstants.ID;
import static to.rtc.rtc2jira.storage.WorkItemConstants.SUMMARY;
import static to.rtc.rtc2jira.storage.WorkItemConstants.WORK_ITEM_TYPE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.LabelService;
import org.eclipse.egit.github.core.service.RepositoryService;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.storage.StorageEngine;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

public class GitHubExporter implements Exporter {

  private static final String TYPE_TASK = "task";
  private static final String TYPE_STORY = "com.ibm.team.apt.workItemType.story";
  private static final String TYPE_EPIC = "com.ibm.team.apt.workItemType.epic";
  private static final String TYPE_BUSINESSNEED = "com.ibm.team.workitem.workItemType.businessneed";

  private StorageEngine storageEngine;
  private GitHubClient client;
  private RepositoryService service;
  private Repository repository;
  private Settings settings;

  @Override
  public boolean isConfigured() {
    boolean isConfigured = false;
    if (settings.hasGithubProperties()) {
      client.setCredentials(settings.getGithubUser(), settings.getGithubPassword());
      client.setOAuth2Token(settings.getGithubToken());
      try {
        repository =
            service.getRepository(settings.getGithubRepoOwner(), settings.getGithubRepoName());
        isConfigured = true;
      } catch (IOException e) {
        System.out.println("Couldnt access github repository");
        e.printStackTrace();
      }
    }
    return isConfigured;
  }

  @Override
  public void initialize(Settings settings, StorageEngine engine) {
    this.settings = settings;
    this.storageEngine = engine;
    this.client = new GitHubClient();
    this.service = new RepositoryService(client);
  }

  public void export() throws Exception {
    IssueService issueService = new IssueService(client);
    for (ODocument workItem : getWorkItems()) {
      Issue issue = new Issue();

      for (Entry<String, Object> entry : workItem) {
        String field = entry.getKey();
        switch (field) {
          case ID:
            String id = (String) entry.getValue();
            issue.setNumber(Integer.valueOf(id));
            break;
          case SUMMARY:
            String summary = (String) entry.getValue();
            issue.setTitle(summary);
            break;
          case DESCRIPTION:
            String htmlText = (String) entry.getValue();
            issue.setBody(htmlText);
            break;
          case WORK_ITEM_TYPE:
            String workitemType = (String) entry.getValue();
            switch (workitemType) {
              case TYPE_TASK:
                issue.setLabels(Collections.singletonList(getLabel("Task")));
                break;
              case TYPE_STORY:
                issue.setLabels(Collections.singletonList(getLabel("Story")));
                break;
              case TYPE_EPIC:
                issue.setLabels(Collections.singletonList(getLabel("Epic")));
                break;
              case TYPE_BUSINESSNEED:
                issue.setLabels(Collections.singletonList(getLabel("Busines Need")));
                break;
              default:
                System.out.println("Cannot create label for unknown workitemType: " + workitemType);
                break;
            }
            break;
          default:
            break;
        }
      }
      issue.setTitle(issue.getNumber() + ": " + issue.getTitle());
      issueService.createIssue(repository, issue);
    }
  }

  private List<ODocument> getWorkItems() {
    final List<ODocument> result = new ArrayList<>();
    storageEngine.withDB(db -> {
      OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<ODocument>("select * from WorkItem");
      List<ODocument> queryResults = db.query(query);
      result.addAll(queryResults);
    });
    return result;
  }

  private Label getLabel(String name) throws IOException {
    LabelService labelService = new LabelService(client);
    List<Label> labels = labelService.getLabels(repository);
    for (Label label : labels) {
      if (label.getName().toLowerCase().equals(name.toLowerCase())) {
        return label;
      }
    }

    Label label = new Label();
    label.setName(name);
    label.setColor(getRandomColorAsHex());
    labelService.createLabel(repository, label);
    return label;
  }

  private String getRandomColorAsHex() {
    Random colorRandom = new Random();
    int maxColor = Integer.valueOf("FFFFFF", 16);
    int color = colorRandom.nextInt(maxColor);
    String colorAsHex = Integer.toHexString(color);
    return colorAsHex;
  }

}
