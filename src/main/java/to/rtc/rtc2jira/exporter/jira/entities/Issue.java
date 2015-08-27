package to.rtc.rtc2jira.exporter.jira.entities;

import static to.rtc.rtc2jira.storage.FieldNames.DESCRIPTION;
import static to.rtc.rtc2jira.storage.FieldNames.ID;
import static to.rtc.rtc2jira.storage.FieldNames.SUMMARY;
import static to.rtc.rtc2jira.storage.FieldNames.WORK_ITEM_TYPE;
import static to.rtc.rtc2jira.storage.WorkItemTypes.BUSINESSNEED;
import static to.rtc.rtc2jira.storage.WorkItemTypes.DEFECT;
import static to.rtc.rtc2jira.storage.WorkItemTypes.EPIC;
import static to.rtc.rtc2jira.storage.WorkItemTypes.STORY;
import static to.rtc.rtc2jira.storage.WorkItemTypes.TASK;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlRootElement;

import to.rtc.rtc2jira.exporter.jira.JiraPersistence;
import to.rtc.rtc2jira.exporter.jira.JiraRestAccess;
import to.rtc.rtc2jira.storage.Comment;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageQuery;

import com.orientechnologies.orient.core.record.impl.ODocument;

@XmlRootElement
public class Issue extends BaseEntity {
  private static final Logger LOGGER = Logger.getLogger(Issue.class.getName());

  private static Map<String, List<IssueType>> existingIssueTypes = null;

  private String expand;
  private String key;
  private IssueFields fields;


  public static Issue createFromWorkItem(ODocument workItem) {
    Project project = JiraPersistence.getInstance().getProject();
    Issue issue = new Issue();
    IssueFields issueFields = issue.getFields();
    issueFields.setProject(project);
    for (Entry<String, Object> entry : workItem) {
      String field = entry.getKey();
      switch (field) {
        case ID:
          String id = (String) entry.getValue();
          issue.setId(id);
          break;
        case SUMMARY:
          String summary = (String) entry.getValue();
          issueFields.setSummary(summary);
          break;
        case DESCRIPTION:
          String htmlText = (String) entry.getValue();
          // TODO: replace HTML style formatting with JIRA formatting
          issueFields.setDescription(htmlText);
          break;
        case FieldNames.COMMENTS:
          @SuppressWarnings("unchecked")
          List<Comment> comments = (List<Comment>) entry.getValue();
          if (comments.size() > 0) {
            IssueCommentContainer issueCommentContainer = new IssueCommentContainer();
            List<IssueComment> commentList = issueCommentContainer.getComments();
            for (Comment comment : comments) {
              commentList.add(IssueComment.createWithBody(issue, comment.getComment()));
            }
            issueFields.setComment(issueCommentContainer);
          }
          break;
        case FieldNames.SEVERITY:
          String severity = (String) entry.getValue();
          SeverityEnum severityEnum = SeverityEnum.fromRtcLiteral(severity);
          IssuePriority issuePrio = IssuePriority.createWithId(severityEnum.getJiraId());
          issuePrio.setId(severityEnum.getJiraId());
          issueFields.setPriority(issuePrio);
          break;
        case FieldNames.DUE_DATE:
          Date dueDate = (Date) entry.getValue();
          issueFields.setDueDate(dueDate);
          break;
        case WORK_ITEM_TYPE:
          String workitemType = (String) entry.getValue();
          switch (workitemType) {
            case TASK:
              issueFields.setIssuetype(getIssueType("Task"));
              break;
            case STORY:
              issueFields.setIssuetype(getIssueType("User Story"));
              break;
            case EPIC:
              issueFields.setIssuetype(getIssueType("Epic"));
              break;
            case BUSINESSNEED:
              issueFields.setIssuetype(getIssueType("Business Need"));
              break;
            case DEFECT:
              issueFields.setIssuetype(getIssueType("Bug"));
              break;
            default:
              LOGGER
                  .warning("Cannot determine issuetype for unknown workitemType: " + workitemType);
              break;
          }
          break;
        default:
          break;
      }
    }
    issueFields.setSummary(issue.getId() + ": " + issueFields.getSummary());
    issue.setId(StorageQuery.getField(workItem, FieldNames.JIRA_ID_LINK, ""));
    issue.setKey(StorageQuery.getField(workItem, FieldNames.JIRA_KEY_LINK, ""));
    return issue;
  }


  public String getExpand() {
    return expand;
  }

  public void setExpand(String expand) {
    this.expand = expand;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public IssueFields getFields() {
    if (fields == null) {
      fields = new IssueFields();
    }
    return fields;
  }

  public void setFields(IssueFields fields) {
    this.fields = fields;
  }

  @Override
  public String getPath() {
    return "/issue";
  }

  @SuppressWarnings("unchecked")
  @Override
  public Optional<Issue> save() {
    return (Optional<Issue>) super.save();
  }

  private static IssueType getIssueType(String issuetypeName) {
    String projectKey = JiraPersistence.getInstance().getProject().getKey();
    JiraRestAccess restAccess = JiraPersistence.getInstance().getRestAccess();
    if (existingIssueTypes == null) {
      IssueMetadata issueMetadata =
          restAccess.get("/issue/createmeta/?expand=projects.issuetypes.fields.",
              IssueMetadata.class);
      existingIssueTypes = new HashMap<String, List<IssueType>>();
      existingIssueTypes
          .put(projectKey, issueMetadata.getProject(projectKey).get().getIssuetypes());
    }

    List<IssueType> issuesTypesByProject = existingIssueTypes.get(projectKey);
    IssueType issueType =
        getIssueTypeByName(issuetypeName, issuesTypesByProject).orElse(
            createIssueType(issuetypeName));

    if (!issuesTypesByProject.contains(issueType)) {
      issuesTypesByProject.add(issueType);
    }
    return issueType;
  }

  private static IssueType createIssueType(String issuetypeName) {
    JiraRestAccess restAccess = JiraPersistence.getInstance().getRestAccess();
    IssueType newIssueType = new IssueType();
    newIssueType.setName(issuetypeName);
    newIssueType = restAccess.post("/issuetype", newIssueType, IssueType.class);
    return newIssueType;
  }

  private static Optional<IssueType> getIssueTypeByName(String name, Collection<IssueType> types) {
    List<IssueType> filteredTypes =
        types.stream().filter(issuetype -> issuetype.getName().equals(name))
            .collect(Collectors.toList());
    if (filteredTypes.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(filteredTypes.get(0));
    }

  }


}
