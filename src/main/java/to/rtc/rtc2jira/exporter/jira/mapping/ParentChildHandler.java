package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.JiraExporter;
import to.rtc.rtc2jira.exporter.jira.JiraRestAccess;
import to.rtc.rtc2jira.exporter.jira.entities.AddIssueLink;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueLinkType;

public class ParentChildHandler extends LinkHandler {

  public static final ParentChildHandler INSTANCE;

  static {
    INSTANCE = new ParentChildHandler(JiraExporter.INSTANCE.getRestAccess());
  }

  ParentChildHandler(JiraRestAccess access) {
    super(access);
  }


  public void addParentChildLink(Issue child, Issue parent) {
    AddIssueLink addIssueLink = AddIssueLink.createAddLink(IssueLinkType.RELATES, child, parent);
    addIssueLink = linkIssues(addIssueLink);
    if (addIssueLink != null) {
      child.getFields().getIssuelinks().add(addIssueLink);
    }
  }


}
