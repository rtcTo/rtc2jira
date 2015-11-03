package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.JiraExporter;
import to.rtc.rtc2jira.exporter.jira.JiraRestAccess;
import to.rtc.rtc2jira.exporter.jira.entities.AddIssueLink;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueLinkType;

public class StoryTaskHandler extends LinkHandler {

  public static final StoryTaskHandler INSTANCE;

  static {
    INSTANCE = new StoryTaskHandler(JiraExporter.INSTANCE.getRestAccess());
  }

  StoryTaskHandler(JiraRestAccess access) {
    super(access);
  }


  public void addTaskToStory(Issue task, Issue story) {
    AddIssueLink addIssueLink = AddIssueLink.createAddLink(IssueLinkType.STORY_TASKS, task, story);
    linkIssues(addIssueLink);
  }


}
