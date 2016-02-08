package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import to.rtc.rtc2jira.ExportManager;

public enum StateEnum {
  migr_resolved("5", "Resolved", "3", "com.ibm.team.workitem.impedimentWorkflow.state.s2") {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnum, String> getTransitionMap() {
      return StateEnum.getMigrationTransitionMap(this);
    }
  }, // 51 business need
  migr_reopened("4", "Reopened", "6") {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnum, String> getTransitionMap() {
      return StateEnum.getMigrationTransitionMap(this);
    }
  }, // 51 business need
  migr_invalid("10105", "Invalid", "com.ibm.team.apt.epic.workflow.state.s6",
      "com.ibm.team.apt.storyWorkflow.state.s2", "com.ibm.team.workitem.impedimentWorkflow.state.s3",
      "com.ibm.team.workitem.taskWorkflow.state.s4") {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnum, String> getTransitionMap() {
      return StateEnum.getMigrationTransitionMap(this);
    }
  }, // 51 business need
  migr_implemented("10104", "Implemented", "com.ibm.team.apt.story.tested") {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnum, String> getTransitionMap() {
      return StateEnum.getMigrationTransitionMap(this);
    }
  }, // 51 business need
  migr_done("10001", "Done", "com.ibm.team.apt.epic.workflow.state.s3", "com.ibm.team.apt.story.verified") {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnum, String> getTransitionMap() {
      return StateEnum.getMigrationTransitionMap(this);
    }

    @Override
    public boolean isFinished() {
      return true;
    }

  }, // 51 business need
  migr_new("10108", "Start", "1", "com.ibm.team.apt.epic.workflow.state.s1", "com.ibm.team.apt.story.idea",
      "com.ibm.team.workitem.impedimentWorkflow.state.s1") {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnum, String> getTransitionMap() {
      return StateEnum.getMigrationTransitionMap(this);
    }
  }, // 51 business need
  migr_unqualified("10109", "Unqualified", "51") {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnum, String> getTransitionMap() {
      return StateEnum.getMigrationTransitionMap(this);
    }
  }, // 51 business need
  migr_qualified("10106", "Qualified", "52") {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnum, String> getTransitionMap() {
      return StateEnum.getMigrationTransitionMap(this);
    }
  }, // ? just guessing
  migr_estimationOk("10103", "Estimation OK", "com.ibm.team.workitem.businessneedWorkflow.state.s2") {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnum, String> getTransitionMap() {
      return StateEnum.getMigrationTransitionMap(this);
    }
  }, //
  migr_approved("10100", "Approved", "53") {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnum, String> getTransitionMap() {
      return StateEnum.getMigrationTransitionMap(this);
    }
  }, // 53 business need
  migr_readyProduction("10107", "Ready for Production", "com.ibm.team.workitem.businessneedWorkflow.state.s5") {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnum, String> getTransitionMap() {
      return StateEnum.getMigrationTransitionMap(this);
    }
  }, // business
  migr_inProgress("3", "In Progress", "2", "com.ibm.team.apt.epic.workflow.state.s2", "com.ibm.team.apt.story.defined",
      "com.ibm.team.workitem.businessneedWorkflow.state.s7") {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createInProgress();
    }

    @Override
    protected Map<StateEnum, String> getTransitionMap() {
      return StateEnum.getMigrationTransitionMap(this);
    }
  }, //
  migr_developmentDone("10102", "Development Done", "com.ibm.team.workitem.businessneedWorkflow.state.s6") {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createInProgress();
    }

    @Override
    protected Map<StateEnum, String> getTransitionMap() {
      return StateEnum.getMigrationTransitionMap(this);
    }
  }, //
  migr_deferred("10101", "Deferred", "com.ibm.team.apt.epic.workflow.state.s5",
      "com.ibm.team.apt.storyWorkflow.state.s1", "54") {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnum, String> getTransitionMap() {
      return StateEnum.getMigrationTransitionMap(this);
    }
  },
  migr_closed("6", "Closed", "4", "55") {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createDone();
    }

    @Override
    protected Map<StateEnum, String> getTransitionMap() {
      return StateEnum.getMigrationTransitionMap(this);
    }

    @Override
    public boolean isFinished() {
      return true;
    }

  };

  public static final String NO_TRANSITION = "noFriggingTransition";

  static private final Logger LOGGER = Logger.getLogger(StateEnum.class.getName());
  static {
    LOGGER.addHandler(ExportManager.DEFAULT_LOG_HANDLER);
  }

  private Set<String> rtcId;
  private String jiraId;
  private String statusName;
  private IssueStatus issueStatus;

  private StateEnum(String jiraId, String statusName, String... rtcId) {
    this.rtcId = new HashSet<String>(Arrays.asList(rtcId));
    this.jiraId = jiraId;
    this.statusName = statusName;
  }

  public Set<String> getRtcId() {
    return rtcId;
  }

  public String getJiraId() {
    return jiraId;
  }


  public String getStatusName() {
    return statusName;
  }

  public IssueStatus getIssueStatus() {
    if (issueStatus == null) {
      issueStatus = createIssueStatus();
      issueStatus.setStatusCategory(StatusCategory.createToDo());
    }
    return issueStatus;
  }

  public CustomFieldOption getCustomFieldOption() {
    return new CustomFieldOption(getJiraId());
  }

  public static Optional<StateEnum> forJiraId(String jiraId, IssueType issueType) {
    Optional<StateEnum> first =
        EnumSet.allOf(StateEnum.class).stream().filter(item -> item.getJiraId().equals(jiraId)).findFirst();
    if (first.isPresent()) {
      first = Optional.of(first.get());
    } else if ("1".equals(jiraId)) {
      first = Optional.of(StateEnum.migr_new);
    }
    return first;
  }

  public static Optional<StateEnum> forRtcId(String rtcId, IssueType issueType) {
    // handle adoption item
    if ("com.ibm.team.rtc.workflow.adoption.state.s1".equals(rtcId)) {
      return Optional.of(migr_new);
    } else if ("com.ibm.team.rtc.workflow.adoption.state.s2".equals(rtcId)) {
      return Optional.of(migr_inProgress);
    } else if ("com.ibm.team.rtc.workflow.adoption.state.s3".equals(rtcId)) {
      return Optional.of(migr_invalid);
    } else if ("com.ibm.team.rtc.workflow.adoption.state.s4".equals(rtcId)) {
      return Optional.of(migr_done);
    }

    Optional<StateEnum> first =
        EnumSet.allOf(StateEnum.class).stream().filter(item -> item.getRtcId().contains(rtcId)).findFirst();
    if (first.isPresent()) {
      first = Optional.of(first.get());
    }
    return first;
  }

  protected IssueStatus createIssueStatus() {
    IssueStatus result = new IssueStatus();
    result.setId(this.getJiraId());
    result.setName(this.getStatusName());
    result.setStatusCategory(createStatusCategory());
    return result;
  }

  public boolean isFinished() {
    return false;
  }

  protected abstract StatusCategory createStatusCategory();

  public List<String> getTransitionPath(StateEnum targetStatus) {
    return getTransitionPath(targetStatus, false);
  }

  public List<String> forceTransitionPath(StateEnum targetStatus) {
    return getTransitionPath(targetStatus, true);
  }

  /**
   * The implementation.
   * 
   * @param targetStatus
   * @param force useful when need to temporarily navigate to editable state, then back
   * @return
   */
  private List<String> getTransitionPath(StateEnum targetStatus, boolean force) {
    List<String> path = new ArrayList<String>();
    String directTansId = getTransitionId(targetStatus);
    // handle no direct path
    if ((NO_TRANSITION.equals(directTansId) && (!this.equals(targetStatus)) || force)) {
      Map<StateEnum, String> transitionMap = new HashMap<StateEnum, String>();
      transitionMap = getTransitionMap();
      Set<StateEnum> keySet = transitionMap.keySet();
      for (StateEnum stateEnum : keySet) {
        if (stateEnum == targetStatus)
          continue;
        String intermediateTransId = getTransitionId(stateEnum);
        if (!NO_TRANSITION.equals(intermediateTransId)) {
          String finalTransId = stateEnum.getTransitionId(targetStatus);
          if (!NO_TRANSITION.equals(finalTransId)) {
            path.add(intermediateTransId);
            path.add(finalTransId);
            break;
          }
        }
      }
    } else {
      path.add(directTansId);
    }
    return path;
  }


  private String getTransitionId(StateEnum targetStatus) {
    Map<StateEnum, String> transitionMap = getTransitionMap();
    String result = transitionMap.get(targetStatus);
    if (result == null) {
      throw new IllegalArgumentException("Target status for transition from start state '" + this.name()
          + "' to target state '" + targetStatus.name() + "' is not handled");
    }

    return result;
  }


  abstract protected Map<StateEnum, String> getTransitionMap();

  // Override this in "done" states
  public boolean isEditable() {
    return true;
  }

  static private Map<StateEnum, String> getMigrationTransitionMap(StateEnum fromState) {
    Map<StateEnum, String> transitionMap = new HashMap<StateEnum, String>();
    transitionMap.put(migr_resolved, "71");
    transitionMap.put(migr_reopened, "61");
    transitionMap.put(migr_invalid, "51");
    transitionMap.put(migr_implemented, "41");
    transitionMap.put(migr_new, "21");
    transitionMap.put(migr_done, "31");
    transitionMap.put(migr_unqualified, "11");
    transitionMap.put(migr_qualified, "81");
    transitionMap.put(migr_approved, "101");
    transitionMap.put(migr_estimationOk, "111");
    transitionMap.put(migr_readyProduction, "121");
    transitionMap.put(migr_inProgress, "131");
    transitionMap.put(migr_developmentDone, "141");
    transitionMap.put(migr_closed, "151");
    transitionMap.put(migr_deferred, "91");
    // replace from state value
    transitionMap.put(fromState, NO_TRANSITION);
    return transitionMap;

  }

}
