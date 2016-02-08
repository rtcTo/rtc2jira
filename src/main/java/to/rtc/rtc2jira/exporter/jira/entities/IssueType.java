package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonView;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueType extends NamedEntity {

  public static final IssueType TASK;
  public static final IssueType SUB_TASK;
  public static final IssueType USER_STORY;
  public static final IssueType STORY;
  public static final IssueType EPIC;
  public static final IssueType BUG;
  public static final IssueType NEW_FEATURE;
  public static final IssueType IMPROVEMENT;
  public static final IssueType BUSINESS_NEED;
  public static final IssueType IMPEDIMENT;
  public static final IssueType CATEGORY;
  public static final IssueType ITERATION;

  static {
    TASK = new IssueType("10100", "Task");
    SUB_TASK = new IssueType("10101", "Sub-task");
    SUB_TASK.setSubtask(true);
    USER_STORY = new IssueType("11111", "User Story");
    STORY = new IssueType("10001", "Story");
    EPIC = new IssueType("10000", "Epic");
    BUG = new IssueType("10102", "Bug");
    NEW_FEATURE = new IssueType("22222", "New Feature");
    IMPROVEMENT = new IssueType("33333", "Improvement");
    BUSINESS_NEED = new IssueType("10200", "Business Need");
    IMPEDIMENT = new IssueType("10201", "Impediment");
    CATEGORY = new IssueType("44444", "Category");
    ITERATION = new IssueType("10202", "Iteration");
  }

  public IssueType() {}

  public IssueType(String id, String name) {
    super(id, name);
  }

  private String description;
  private boolean subtask;

  @JsonView(IssueView.Update.class)
  @Override
  public String getKey() {
    return super.getKey();
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @JsonView(IssueView.Update.class)
  public boolean isSubtask() {
    return subtask;
  }

  public void setSubtask(boolean subtask) {
    this.subtask = subtask;
  }

  @Override
  public String getPath() {
    return "/issuetype";
  }

  @Override
  public String getSelfPath() {
    return "/issuetype/" + getId();
  }

  @Override
  public int hashCode() {
    int prime = 37;
    int result = 1;
    result = result * prime + getId().hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof IssueType)) {
      return false;
    }
    return this.getId().equals(((IssueType) obj).getId());
  }


}
