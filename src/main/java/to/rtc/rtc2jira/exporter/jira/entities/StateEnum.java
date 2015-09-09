package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public enum StateEnum {
  abandoned(StatusEnum.done, "com.ibm.team.workitem.buildTrackingWorkflow.state.s7"), //
  done(StatusEnum.done, "com.ibm.team.workitem.buildTrackingWorkflow.state.s3",
      "com.ibm.team.workitem.retrospectiveWorkflow.state.finished", "com.ibm.team.apt.epic.workflow.state.s3",
      "com.ibm.team.apt.story.verified"), //
  inProgress(StatusEnum.inprogress, "com.ibm.team.workitem.buildTrackingWorkflow.state.s2", "2",
      "com.ibm.team.workitem.retrospectiveWorkflow.state.inprogress", "com.ibm.team.apt.epic.workflow.state.s2",
      "com.ibm.team.apt.story.defined"), //
  neww(StatusEnum.todo, "1", "com.ibm.team.workitem.retrospectiveWorkflow.state.new",
      "com.ibm.team.apt.epic.workflow.state.s1", "com.ibm.team.workitem.impedimentWorkflow.state.s1",
      "com.ibm.team.apt.story.idea"), //
  reopened(StatusEnum.todo, "6"), //
  resolved(StatusEnum.done, "3", "com.ibm.team.workitem.impedimentWorkflow.state.s2"), //
  verified(StatusEnum.done, "4"), //
  approved(StatusEnum.done, "com.ibm.team.rtc.workflow.adoption.state.s2"), //
  completed(StatusEnum.done, "com.ibm.team.rtc.workflow.adoption.state.s4"), //
  proposed(StatusEnum.todo, "com.ibm.team.rtc.workflow.adoption.state.s1"), //
  rejected(StatusEnum.done, "com.ibm.team.rtc.workflow.adoption.state.s3"), //
  invalid(StatusEnum.done, "com.ibm.team.workitem.retrospectiveWorkflow.state.s1",
      "com.ibm.team.workitem.taskWorkflow.state.s4", "com.ibm.team.apt.epic.workflow.state.s6",
      "com.ibm.team.workitem.impedimentWorkflow.state.s3", "com.ibm.team.apt.storyWorkflow.state.s2"), //
  deferred(StatusEnum.todo, "com.ibm.team.apt.epic.workflow.state.s5", "com.ibm.team.apt.storyWorkflow.state.s1"), //
  implemented(StatusEnum.done, "com.ibm.team.apt.story.tested");


  final private StatusEnum jiraStatus;
  final private Set<String> rctLiterals;

  private StateEnum(StatusEnum jiraStatus, String... rtcLiterals) {
    this.jiraStatus = jiraStatus;
    this.rctLiterals = new HashSet<String>(Arrays.asList(rtcLiterals));
  }

  public StatusEnum getStatusEnum() {
    return jiraStatus;
  }

  public Set<String> getRctLiterals() {
    return rctLiterals;
  }

  public static Optional<StateEnum> forRtcLiteral(String literal) {
    EnumSet<StateEnum> all = EnumSet.allOf(StateEnum.class);
    return all.stream().filter(item -> item.getRctLiterals().contains(literal)).findFirst();
  }

  public static Optional<StateEnum> forJiraLiteral(String literal) {
    EnumSet<StateEnum> all = EnumSet.allOf(StateEnum.class);
    return all.stream().filter(item -> item.getStatusEnum().getJiraId().equals(literal)).findFirst();
  }


}
