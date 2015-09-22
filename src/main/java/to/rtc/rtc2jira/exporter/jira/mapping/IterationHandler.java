package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.JiraExporter;
import to.rtc.rtc2jira.exporter.jira.JiraRestAccess;
import to.rtc.rtc2jira.exporter.jira.entities.AddIssueLink;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueLinkType;
import to.rtc.rtc2jira.exporter.jira.entities.IssueType;
import to.rtc.rtc2jira.exporter.jira.entities.Project;
import to.rtc.rtc2jira.importer.mapping.TargetMapping.IterationInfo;

public class IterationHandler extends LinkHandler {
  public static final IterationHandler INSTANCE;

  static {
    INSTANCE = new IterationHandler(JiraExporter.INSTANCE.getRestAccess());
  }

  private IterationHandler(JiraRestAccess access) {
    super(access);
  }

  Issue getIterationIssue(IterationInfo iterInfo, Project project) {
    Issue iterationIssue = getIssueBySummary(iterInfo.name, IssueType.ITERATION, project);
    if (iterInfo.parent != null) {
      if (iterationIssue.hierarchyLinks().isEmpty()) {
        Issue parent = getIterationIssue(iterInfo.parent, project);
        AddIssueLink addIssueLink = AddIssueLink.createAddLink(IssueLinkType.HIERARCHY, iterationIssue, parent);
        linkIssues(addIssueLink);
      }
    }
    return iterationIssue;
  }

}
