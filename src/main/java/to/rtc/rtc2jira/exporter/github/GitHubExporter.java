package to.rtc.rtc2jira.exporter.github;

import static to.rtc.rtc2jira.storage.FieldNames.DESCRIPTION;
import static to.rtc.rtc2jira.storage.FieldNames.ID;
import static to.rtc.rtc2jira.storage.FieldNames.SUMMARY;
import static to.rtc.rtc2jira.storage.FieldNames.WORK_ITEM_TYPE;
import static to.rtc.rtc2jira.storage.WorkItemTypes.BUSINESSNEED;
import static to.rtc.rtc2jira.storage.WorkItemTypes.DEFECT;
import static to.rtc.rtc2jira.storage.WorkItemTypes.EPIC;
import static to.rtc.rtc2jira.storage.WorkItemTypes.STORY;
import static to.rtc.rtc2jira.storage.WorkItemTypes.TASK;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.LabelService;
import org.eclipse.egit.github.core.service.RepositoryService;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;
import to.rtc.rtc2jira.storage.StorageQuery;

import com.orientechnologies.orient.core.record.impl.ODocument;

public class GitHubExporter implements Exporter {

  private static final Logger LOGGER = Logger.getLogger(GitHubExporter.class.getName());
  private GitHubClient client;
  private RepositoryService service;
  private Repository repository;
  private IssueService issueService;
  private GitHubStorage store;

  @Override
  public boolean isConfigured() {
    return Settings.getInstance().hasGithubProperties();
  }

  @Override
  public void initialize(Settings settings, StorageEngine engine) throws IOException {
    this.store = new GitHubStorage(engine);
    this.client = new GitHubClient();
    this.service = new RepositoryService(client);
    this.issueService = new IssueService(client);
    client.setCredentials(settings.getGithubUser(), settings.getGithubPassword());
    client.setOAuth2Token(settings.getGithubToken());
    repository = service.getRepository(settings.getGithubRepoOwner(), settings.getGithubRepoName());
  }

  @Override
  public void createOrUpdateItem(ODocument item) throws Exception {
    Issue issue = createIssueFromWorkItem(item);
    Issue gitHubIssue = createGitHubIssue(issue);
    store.storeLinkToIssueInWorkItem(Optional.ofNullable(gitHubIssue), item);
  }

  private Issue createIssueFromWorkItem(ODocument workItem) throws IOException {
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
            case TASK:
              issue.setLabels(Collections.singletonList(getLabel("Task")));
              break;
            case STORY:
              issue.setLabels(Collections.singletonList(getLabel("Story")));
              break;
            case EPIC:
              issue.setLabels(Collections.singletonList(getLabel("Epic")));
              break;
            case BUSINESSNEED:
              issue.setLabels(Collections.singletonList(getLabel("Business Need")));
              break;
            case DEFECT:
              issue.setLabels(Collections.singletonList(getLabel("Defect")));
              break;
            default:
              LOGGER.warning("Cannot create label for unknown workitemType: " + workitemType);
              break;
          }
          break;
        default:
          break;
      }
    }
    issue.setTitle(issue.getNumber() + ": " + issue.getTitle());
    int existingGitHubIssueNumber = StorageQuery.getField(workItem, FieldNames.GITHUB_WORKITEM_LINK, 0);
    issue.setNumber(existingGitHubIssueNumber);
    return issue;
  }

  private Issue createGitHubIssue(Issue issue) throws IOException {
    boolean isAlreadyCreated = issue.getNumber() != 0;
    Issue createdIssue = null;
    if (!isAlreadyCreated) {
      createdIssue = issueService.createIssue(repository, issue);
    } else {
      issueService.editIssue(repository, issue);
    }
    return createdIssue;
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

  @Override
  public void postExport() throws Exception {
    // do nothing
  }

}
