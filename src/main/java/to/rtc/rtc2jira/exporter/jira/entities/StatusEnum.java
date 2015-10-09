package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.Settings;

public enum StatusEnum {

  todo("10000", "To Do") {
    @Override
    public IssueStatus getIssueStatus() {
      IssueStatus result = createIssueStatus();
      result.setStatusCategory(StatusCategory.createToDo());
      return result;
    }

    @Override
    public String getTransitionId(StatusEnum targetStatus) {
      switch (targetStatus) {
        case todo:
          return NO_TRANSITION;
        case inprogress:
          return Settings.getInstance().getJiraTransitionTodoInprogress();
        case done:
          return Settings.getInstance().getJiraTransitionTodoDone();
      }
      return NO_TRANSITION;
    }

  },
  inprogress("3", "In Progress") {
    @Override
    public IssueStatus getIssueStatus() {
      IssueStatus result = createIssueStatus();
      result.setStatusCategory(StatusCategory.createInProgress());
      return result;
    }

    @Override
    public String getTransitionId(StatusEnum targetStatus) {
      switch (targetStatus) {
        case inprogress:
          return NO_TRANSITION;
        case todo:
          return Settings.getInstance().getJiraTransitionInprogressTodo();
        case done:
          return Settings.getInstance().getJiraTransitionInprogressDone();
      }
      return NO_TRANSITION;
    }

  },
  done("10001", "Done") {
    @Override
    public IssueStatus getIssueStatus() {
      IssueStatus result = createIssueStatus();
      result.setStatusCategory(StatusCategory.createDone());
      return result;
    }

    @Override
    public String getTransitionId(StatusEnum targetStatus) {
      switch (targetStatus) {
        case done:
          return NO_TRANSITION;
        case inprogress:
          return Settings.getInstance().getJiraTransitionDoneInprogress();
        case todo:
          return Settings.getInstance().getJiraTransitionDoneTodo();
      }
      return NO_TRANSITION;
    }

  };


  private StatusEnum(String jiraId, String name) {
    this.jiraId = jiraId;
    this.statusName = name;
  }

  public static final String NO_TRANSITION = "noFriggingTransition";
  private String jiraId;
  private String statusName;
  static private final Logger LOGGER = Logger.getLogger(StatusEnum.class.getName());

  public String getJiraId() {
    return jiraId;
  }

  public String getStatusName() {
    return statusName;
  }

  public abstract IssueStatus getIssueStatus();


  protected IssueStatus createIssueStatus() {
    IssueStatus result = new IssueStatus();
    result.setId(this.getJiraId());
    result.setName(this.getStatusName());

    switch (this) {
      case done:
        result.setStatusCategory(StatusCategory.createDone());
        break;
      case todo:
        result.setStatusCategory(StatusCategory.createToDo());
        break;
      case inprogress:
        result.setStatusCategory(StatusCategory.createInProgress());
        break;
    }

    return result;
  }

  public abstract String getTransitionId(StatusEnum targetStatus);

  public static StatusEnum forJiraId(String jiraId) {
    if (todo.getJiraId().equals(jiraId)) {
      return todo;
    } else if (inprogress.getJiraId().equals(jiraId)) {
      return inprogress;
    } else if (done.getJiraId().equals(jiraId)) {
      return done;
    } else {
      LOGGER.log(Level.SEVERE, "Could not find a StatusEnum entry for the jira id " + jiraId);
      return null;
    }
  }

}
