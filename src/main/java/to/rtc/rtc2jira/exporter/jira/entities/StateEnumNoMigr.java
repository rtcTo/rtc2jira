package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import to.rtc.rtc2jira.ExportManager;

public enum StateEnumNoMigr {
  bug_neww("1", "1", IssueType.BUG, "Open", null) {

    public StateEnumNoMigr getProxy() {
      return this;
    };

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(bug_neww, NO_TRANSITION);
      transitionMap.put(bug_inProgress, "4");
      transitionMap.put(bug_resolved, "5");
      transitionMap.put(bug_verified, "2");
      transitionMap.put(bug_reopened, NO_TRANSITION);
      return transitionMap;
    }
  }, //
  bug_inProgress("3", "2", IssueType.BUG, "In Progress", null) {

    @Override
    public StateEnumNoMigr getProxy() {
      return this;
    }

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(bug_neww, "301");
      transitionMap.put(bug_inProgress, NO_TRANSITION);
      transitionMap.put(bug_resolved, "5");
      transitionMap.put(bug_verified, "2");
      transitionMap.put(bug_reopened, NO_TRANSITION);
      return transitionMap;
    }
  }, //
  bug_resolved("5", "3", IssueType.BUG, "Resolved", null) {
    @Override
    public StateEnumNoMigr getProxy() {
      return this;
    }

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(bug_neww, NO_TRANSITION);
      transitionMap.put(bug_inProgress, NO_TRANSITION);
      transitionMap.put(bug_resolved, NO_TRANSITION);
      transitionMap.put(bug_verified, "701");
      transitionMap.put(bug_reopened, "3");
      return transitionMap;
    }
  }, //
  bug_verified("6", "4", IssueType.BUG, "Closed", null) {
    @Override
    public StateEnumNoMigr getProxy() {
      return this;
    }

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(bug_neww, NO_TRANSITION);
      transitionMap.put(bug_inProgress, NO_TRANSITION);
      transitionMap.put(bug_resolved, NO_TRANSITION);
      transitionMap.put(bug_verified, NO_TRANSITION);
      transitionMap.put(bug_reopened, "3");
      return transitionMap;
    }

    @Override
    public boolean isEditable() {
      return false;
    }

    @Override
    public boolean isFinished() {
      return true;
    }
  }, //
  bug_reopened("4", "6", IssueType.BUG, "Reopened", null) {
    @Override
    public StateEnumNoMigr getProxy() {
      return this;
    }

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(bug_neww, NO_TRANSITION);
      transitionMap.put(bug_inProgress, "4");
      transitionMap.put(bug_resolved, "5");
      transitionMap.put(bug_verified, "2");
      transitionMap.put(bug_reopened, NO_TRANSITION);
      return transitionMap;
    }

  }, //
  //
  //
  ep_neww("1", "com.ibm.team.apt.epic.workflow.state.s1", IssueType.EPIC, "Open", StateEnumNoMigr.bug_neww) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(ep_neww, NO_TRANSITION);
      transitionMap.put(ep_inProgress, "4");
      transitionMap.put(ep_done, NO_TRANSITION);
      transitionMap.put(ep_deferred, "361");
      transitionMap.put(ep_invalid, "371");
      return transitionMap;
    }
  }, //
  ep_inProgress("3", "com.ibm.team.apt.epic.workflow.state.s2", IssueType.EPIC, "In Progress", StateEnumNoMigr.bug_inProgress) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createInProgress();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(ep_neww, "301");
      transitionMap.put(ep_inProgress, NO_TRANSITION);
      transitionMap.put(ep_done, "311");
      transitionMap.put(ep_deferred, "331");
      transitionMap.put(ep_invalid, "321");
      return transitionMap;
    }
  }, //
  ep_done("10001", "com.ibm.team.apt.epic.workflow.state.s3", IssueType.EPIC, "Done", StateEnumNoMigr.bug_verified) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createDone();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(ep_neww, "391");
      transitionMap.put(ep_inProgress, NO_TRANSITION);
      transitionMap.put(ep_done, NO_TRANSITION);
      transitionMap.put(ep_deferred, NO_TRANSITION);
      transitionMap.put(ep_invalid, NO_TRANSITION);
      return transitionMap;
    }

    @Override
    public boolean isFinished() {
      return false;
    }
  }, //
  ep_deferred("10100", "com.ibm.team.apt.epic.workflow.state.s5", IssueType.EPIC, "Deferred", StateEnumNoMigr.bug_reopened) {
    @Override
    protected StatusCategory createStatusCategory() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(ep_neww, "341");
      transitionMap.put(ep_inProgress, "351");
      transitionMap.put(ep_done, NO_TRANSITION);
      transitionMap.put(ep_deferred, NO_TRANSITION);
      transitionMap.put(ep_invalid, NO_TRANSITION);
      return transitionMap;
    }
  }, //
  ep_invalid("10101", "com.ibm.team.apt.epic.workflow.state.s6", IssueType.EPIC, "Invalid", StateEnumNoMigr.bug_reopened) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(ep_neww, "381");
      transitionMap.put(ep_inProgress, NO_TRANSITION);
      transitionMap.put(ep_done, NO_TRANSITION);
      transitionMap.put(ep_deferred, NO_TRANSITION);
      transitionMap.put(ep_invalid, NO_TRANSITION);
      return transitionMap;
    }
  }, //
  //
  //
  us_neww("1", "com.ibm.team.apt.story.idea", IssueType.USER_STORY, "Open", StateEnumNoMigr.bug_neww) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(us_neww, NO_TRANSITION);
      transitionMap.put(us_inProgress, "4");
      transitionMap.put(us_implemented, "331");
      transitionMap.put(us_done, "341");
      transitionMap.put(us_deferred, "311");
      transitionMap.put(us_invalid, "321");
      return transitionMap;
    }
  }, //
  us_inProgress("3", "com.ibm.team.apt.story.defined", IssueType.USER_STORY, "In Progress", StateEnumNoMigr.bug_inProgress) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createInProgress();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(us_neww, "301");
      transitionMap.put(us_inProgress, NO_TRANSITION);
      transitionMap.put(us_implemented, "351");
      transitionMap.put(us_done, NO_TRANSITION);
      transitionMap.put(us_deferred, "361");
      transitionMap.put(us_invalid, NO_TRANSITION);
      return transitionMap;
    }
  }, //
  us_implemented("10102", "com.ibm.team.apt.story.tested", IssueType.USER_STORY, "Implemented", StateEnumNoMigr.bug_resolved) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createInProgress();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(us_neww, "401");
      transitionMap.put(us_inProgress, NO_TRANSITION);
      transitionMap.put(us_implemented, NO_TRANSITION);
      transitionMap.put(us_done, "391");
      transitionMap.put(us_deferred, "381");
      transitionMap.put(us_invalid, NO_TRANSITION);
      return transitionMap;
    }
  }, //
  us_done("10001", "com.ibm.team.apt.story.verified", IssueType.USER_STORY, "Done", StateEnumNoMigr.bug_verified) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createDone();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(us_neww, "411");
      transitionMap.put(us_inProgress, NO_TRANSITION);
      transitionMap.put(us_implemented, NO_TRANSITION);
      transitionMap.put(us_done, NO_TRANSITION);
      transitionMap.put(us_deferred, NO_TRANSITION);
      transitionMap.put(us_invalid, NO_TRANSITION);
      return transitionMap;
    }

    @Override
    public boolean isFinished() {
      return false;
    }

  }, //
  us_deferred("10100", "com.ibm.team.apt.storyWorkflow.state.s1", IssueType.USER_STORY, "Deferred",
      StateEnumNoMigr.bug_reopened) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(us_neww, "431");
      transitionMap.put(us_inProgress, "421");
      transitionMap.put(us_implemented, NO_TRANSITION);
      transitionMap.put(us_done, NO_TRANSITION);
      transitionMap.put(us_deferred, NO_TRANSITION);
      transitionMap.put(us_invalid, "441");
      return transitionMap;
    }
  }, //
  us_invalid("10101", "com.ibm.team.apt.storyWorkflow.state.s2", IssueType.USER_STORY, "Invalid",
      StateEnumNoMigr.bug_reopened) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(us_neww, "371");
      transitionMap.put(us_inProgress, NO_TRANSITION);
      transitionMap.put(us_implemented, NO_TRANSITION);
      transitionMap.put(us_done, NO_TRANSITION);
      transitionMap.put(us_deferred, NO_TRANSITION);
      transitionMap.put(us_invalid, NO_TRANSITION);
      return transitionMap;
    }
  }, //
  //
  //
  bn_unqualified("10103", "51", IssueType.BUSINESS_NEED, "Unqualified", StateEnumNoMigr.bug_neww) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(bn_unqualified, NO_TRANSITION);
      transitionMap.put(bn_qualified, "11");
      transitionMap.put(bn_approved, NO_TRANSITION);
      transitionMap.put(bn_estimationOk, NO_TRANSITION);
      transitionMap.put(bn_readyProduction, NO_TRANSITION);
      transitionMap.put(bn_inProgress, NO_TRANSITION);
      transitionMap.put(bn_developmentDone, NO_TRANSITION);
      transitionMap.put(bn_closed, "31");
      transitionMap.put(bn_deferred, "41");
      return transitionMap;
    }
  }, // 51 business need
  bn_qualified("10104", "52", IssueType.BUSINESS_NEED, "Qualified", StateEnumNoMigr.bug_inProgress) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(bn_unqualified, NO_TRANSITION);
      transitionMap.put(bn_qualified, "21");
      transitionMap.put(bn_approved, "51");
      transitionMap.put(bn_estimationOk, NO_TRANSITION);
      transitionMap.put(bn_readyProduction, NO_TRANSITION);
      transitionMap.put(bn_inProgress, NO_TRANSITION);
      transitionMap.put(bn_developmentDone, NO_TRANSITION);
      transitionMap.put(bn_closed, "71");
      transitionMap.put(bn_deferred, "61");
      return transitionMap;
    }
  }, // ? just guessing
  bn_estimationOk("10106", "com.ibm.team.workitem.businessneedWorkflow.state.s2", IssueType.BUSINESS_NEED,
      "Estimation OK", StateEnumNoMigr.bug_inProgress) {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(bn_unqualified, "151");
      transitionMap.put(bn_qualified, "141");
      transitionMap.put(bn_approved, "131");
      transitionMap.put(bn_estimationOk, NO_TRANSITION);
      transitionMap.put(bn_readyProduction, NO_TRANSITION);
      transitionMap.put(bn_inProgress, NO_TRANSITION);
      transitionMap.put(bn_developmentDone, NO_TRANSITION);
      transitionMap.put(bn_closed, NO_TRANSITION);
      transitionMap.put(bn_deferred, "161");
      return transitionMap;
    }
  }, //
  bn_approved("10105", "53", IssueType.BUSINESS_NEED, "Approved", StateEnumNoMigr.bug_inProgress) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(bn_unqualified, NO_TRANSITION);
      transitionMap.put(bn_qualified, "81");
      transitionMap.put(bn_approved, NO_TRANSITION);
      transitionMap.put(bn_estimationOk, "91");
      transitionMap.put(bn_readyProduction, "101");
      transitionMap.put(bn_inProgress, NO_TRANSITION);
      transitionMap.put(bn_developmentDone, NO_TRANSITION);
      transitionMap.put(bn_closed, "111");
      transitionMap.put(bn_deferred, "121");
      return transitionMap;
    }
  }, // 53 business need
  bn_readyProduction("10107", "com.ibm.team.workitem.businessneedWorkflow.state.s5", IssueType.BUSINESS_NEED,
      "Ready for Production", StateEnumNoMigr.bug_inProgress) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(bn_unqualified, NO_TRANSITION);
      transitionMap.put(bn_qualified, NO_TRANSITION);
      transitionMap.put(bn_approved, "171");
      transitionMap.put(bn_estimationOk, "201");
      transitionMap.put(bn_readyProduction, NO_TRANSITION);
      transitionMap.put(bn_inProgress, "191");
      transitionMap.put(bn_developmentDone, "211");
      transitionMap.put(bn_closed, NO_TRANSITION);
      transitionMap.put(bn_deferred, "181");
      return transitionMap;
    }
  }, // business
  bn_inProgress("3", "com.ibm.team.workitem.businessneedWorkflow.state.s7", IssueType.BUSINESS_NEED, "In Progress",
      StateEnumNoMigr.bug_inProgress) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createInProgress();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(bn_unqualified, NO_TRANSITION);
      transitionMap.put(bn_qualified, NO_TRANSITION);
      transitionMap.put(bn_approved, NO_TRANSITION);
      transitionMap.put(bn_estimationOk, NO_TRANSITION);
      transitionMap.put(bn_readyProduction, "221");
      transitionMap.put(bn_inProgress, NO_TRANSITION);
      transitionMap.put(bn_developmentDone, "231");
      transitionMap.put(bn_closed, NO_TRANSITION);
      transitionMap.put(bn_deferred, "291");
      return transitionMap;
    }
  }, //
  bn_developmentDone("10108", "com.ibm.team.workitem.businessneedWorkflow.state.s6", IssueType.BUSINESS_NEED,
      "Development Done", StateEnumNoMigr.bug_inProgress) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createInProgress();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(bn_unqualified, NO_TRANSITION);
      transitionMap.put(bn_qualified, NO_TRANSITION);
      transitionMap.put(bn_approved, NO_TRANSITION);
      transitionMap.put(bn_estimationOk, NO_TRANSITION);
      transitionMap.put(bn_readyProduction, "241");
      transitionMap.put(bn_inProgress, "251");
      transitionMap.put(bn_developmentDone, NO_TRANSITION);
      transitionMap.put(bn_closed, "261");
      transitionMap.put(bn_deferred, "301");
      return transitionMap;
    }
  }, //
  bn_deferred("10100", "54", IssueType.BUSINESS_NEED, "Deferred", StateEnumNoMigr.bug_reopened) {

    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(bn_unqualified, "391");
      transitionMap.put(bn_qualified, "371");
      transitionMap.put(bn_approved, "321");
      transitionMap.put(bn_estimationOk, "351");
      transitionMap.put(bn_readyProduction, "381");
      transitionMap.put(bn_inProgress, "361");
      transitionMap.put(bn_developmentDone, "341");
      transitionMap.put(bn_closed, "331");
      transitionMap.put(bn_deferred, NO_TRANSITION);
      return transitionMap;
    }
  },
  bn_closed("6", "55", IssueType.BUSINESS_NEED, "Closed", StateEnumNoMigr.bug_verified) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createDone();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(bn_unqualified, "271");
      transitionMap.put(bn_qualified, NO_TRANSITION);
      transitionMap.put(bn_approved, NO_TRANSITION);
      transitionMap.put(bn_estimationOk, NO_TRANSITION);
      transitionMap.put(bn_readyProduction, NO_TRANSITION);
      transitionMap.put(bn_inProgress, "281");
      transitionMap.put(bn_developmentDone, NO_TRANSITION);
      transitionMap.put(bn_closed, NO_TRANSITION);
      transitionMap.put(bn_deferred, "311");
      return transitionMap;
    }

    @Override
    public boolean isFinished() {
      return false;
    }

  },
  //
  //
  imp_neww("1", "com.ibm.team.workitem.impedimentWorkflow.state.s1", IssueType.IMPEDIMENT, "Open", StateEnumNoMigr.bug_neww) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(imp_neww, NO_TRANSITION);
      transitionMap.put(imp_invalid, NO_TRANSITION);
      transitionMap.put(imp_resolved, NO_TRANSITION);
      return transitionMap;
    }
  }, //
  imp_resolved("5", "com.ibm.team.workitem.impedimentWorkflow.state.s2", IssueType.IMPEDIMENT, "Resolved",
      StateEnumNoMigr.bug_resolved) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createDone();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(imp_neww, NO_TRANSITION);
      transitionMap.put(imp_invalid, NO_TRANSITION);
      transitionMap.put(imp_resolved, NO_TRANSITION);
      return transitionMap;
    }

    @Override
    public boolean isFinished() {
      return false;
    }

  }, //
  imp_invalid("10101", "com.ibm.team.workitem.impedimentWorkflow.state.s3", IssueType.IMPEDIMENT, "Invalid",
      StateEnumNoMigr.bug_reopened) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(imp_neww, NO_TRANSITION);
      transitionMap.put(imp_invalid, NO_TRANSITION);
      transitionMap.put(imp_resolved, NO_TRANSITION);
      return transitionMap;
    }
  }, //
  //
  //
  task_neww("1", "1", IssueType.TASK, "Open", StateEnumNoMigr.bug_neww) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(task_neww, NO_TRANSITION);
      transitionMap.put(task_inProgress, "51");
      transitionMap.put(task_invalid, "21");
      transitionMap.put(task_resolved, "11");
      return transitionMap;
    }
  }, //
  task_inProgress("3", "2", IssueType.TASK, "In Progress", StateEnumNoMigr.bug_neww) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(task_neww, "71");
      transitionMap.put(task_inProgress, NO_TRANSITION);
      transitionMap.put(task_invalid, NO_TRANSITION);
      transitionMap.put(task_resolved, "61");
      return transitionMap;
    }
  }, //
  task_resolved("5", "3", IssueType.TASK, "Resolved", StateEnumNoMigr.bug_resolved) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createDone();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(task_neww, "31");
      transitionMap.put(task_inProgress, NO_TRANSITION);
      transitionMap.put(task_invalid, NO_TRANSITION);
      transitionMap.put(task_resolved, NO_TRANSITION);
      return transitionMap;
    }

    @Override
    public boolean isFinished() {
      return false;
    }

  }, //
  task_invalid("10101", "com.ibm.team.workitem.taskWorkflow.state.s4", IssueType.TASK, "Invalid",
      StateEnumNoMigr.bug_reopened) {
    @Override
    protected StatusCategory createStatusCategory() {
      return StatusCategory.createToDo();
    }

    @Override
    protected Map<StateEnumNoMigr, String> getTransitionMap() {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      transitionMap.put(task_neww, "41");
      transitionMap.put(task_inProgress, NO_TRANSITION);
      transitionMap.put(task_invalid, NO_TRANSITION);
      transitionMap.put(task_resolved, NO_TRANSITION);
      return transitionMap;
    }
  }; //

  public static final String NO_TRANSITION = "noFriggingTransition";

  static private final Logger LOGGER = Logger.getLogger(StateEnumNoMigr.class.getName());
  static {
    LOGGER.addHandler(ExportManager.DEFAULT_LOG_HANDLER);
  }

  private String rtcId;
  private String jiraId;
  private IssueType issueType;
  private String statusName;
  private IssueStatus issueStatus;
  private StateEnumNoMigr proxy;

  private StateEnumNoMigr(String jiraId, String rtcId, IssueType issueType, String statusName, StateEnumNoMigr proxy) {
    this.rtcId = rtcId;
    this.jiraId = jiraId;
    this.issueType = issueType;
    this.statusName = statusName;
    this.proxy = proxy;
  }

  public String getRtcId() {
    return rtcId;
  }

  public String getJiraId() {
    return jiraId;
  }

  public IssueType getIssueType() {
    return issueType;
  }

  public String getStatusName() {
    return statusName;
  }

  public StateEnumNoMigr getProxy() {
    return this.proxy;
  }

  public IssueStatus getIssueStatus() {
    if (issueStatus == null) {
      IssueStatus result = createIssueStatus();
      result.setStatusCategory(StatusCategory.createToDo());
    }
    IssueStatus result = createIssueStatus();
    result.setStatusCategory(StatusCategory.createToDo());
    return result;
  }

  public CustomFieldOption getCustomFieldOption() {
    return new CustomFieldOption(getJiraId());
  }

  public static Optional<StateEnumNoMigr> forJiraId(String jiraId, IssueType issueType) {
    EnumSet<StateEnumNoMigr> all = EnumSet.allOf(StateEnumNoMigr.class);
    return all
        .stream()
        .filter(
            item -> item.getJiraId().equals(jiraId) && item.getIssueType() != null
                && item.getIssueType().equals(issueType)).findFirst();
  }

  public static Optional<StateEnumNoMigr> forRtcId(String rtcId, IssueType issueType) {
    // handle adoption item
    if ("com.ibm.team.rtc.workflow.adoption.state.s1".equals(rtcId)) {
      return Optional.of(us_neww);
    } else if ("com.ibm.team.rtc.workflow.adoption.state.s2".equals(rtcId)) {
      return Optional.of(us_inProgress);
    } else if ("com.ibm.team.rtc.workflow.adoption.state.s3".equals(rtcId)) {
      return Optional.of(us_invalid);
    } else if ("com.ibm.team.rtc.workflow.adoption.state.s4".equals(rtcId)) {
      return Optional.of(us_done);
    }

    EnumSet<StateEnumNoMigr> all = EnumSet.allOf(StateEnumNoMigr.class);
    return all
        .stream()
        .filter(
            item -> item.getRtcId().equals(rtcId) && item.getIssueType() != null
                && item.getIssueType().equals(issueType)).findFirst();
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

  public List<String> getTransitionPath(StateEnumNoMigr targetStatus) {
    return getTransitionPath(targetStatus, false);
  }

  public List<String> forceTransitionPath(StateEnumNoMigr targetStatus) {
    return getTransitionPath(targetStatus, true);
  }

  /**
   * The implementation.
   * 
   * @param targetStatus
   * @param force useful when need to temporarily navigate to editable state, then back
   * @return
   */
  private List<String> getTransitionPath(StateEnumNoMigr targetStatus, boolean force) {
    List<String> path = new ArrayList<String>();
    String directTansId = getTransitionId(targetStatus);
    // handle no direct path
    if ((NO_TRANSITION.equals(directTansId) && (!this.equals(targetStatus)) || force)) {
      Map<StateEnumNoMigr, String> transitionMap = new HashMap<StateEnumNoMigr, String>();
      getTransitionMap();
      Set<StateEnumNoMigr> keySet = transitionMap.keySet();
      for (StateEnumNoMigr stateEnum : keySet) {
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


  private String getTransitionId(StateEnumNoMigr targetStatus) {
    if (!this.getIssueType().equals(targetStatus.getIssueType()))
      throw new IllegalArgumentException("Transition from start state '" + this.name() + "' to target state '"
          + targetStatus.name() + "' is not possible because they don't belong to the same set");
    Map<StateEnumNoMigr, String> transitionMap = getTransitionMap();
    String result = transitionMap.get(targetStatus);
    if (result == null) {
      throw new IllegalArgumentException("Target status for transition from start state '" + this.name()
          + "' to target state '" + targetStatus.name() + "' is not handled");
    }

    return result;
  }


  abstract protected Map<StateEnumNoMigr, String> getTransitionMap();

  // Override this in "done" states
  public boolean isEditable() {
    return true;
  }


}
