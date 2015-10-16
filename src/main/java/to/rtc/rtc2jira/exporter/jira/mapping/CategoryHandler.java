package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.JiraExporter;
import to.rtc.rtc2jira.exporter.jira.JiraRestAccess;
import to.rtc.rtc2jira.exporter.jira.entities.AddIssueLink;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueLinkType;
import to.rtc.rtc2jira.exporter.jira.entities.IssueType;
import to.rtc.rtc2jira.exporter.jira.entities.Project;

public class CategoryHandler extends LinkHandler {
  public static final CategoryHandler INSTANCE;

  static {
    INSTANCE = new CategoryHandler(JiraExporter.INSTANCE.getRestAccess());
  }

  CategoryHandler(JiraRestAccess access) {
    super(access);
  }

  Issue getCategoryByQualifiedName(String qualifiedName, Project project) {
    String[] split = qualifiedName.split("/");
    Issue previousCategory = null;
    Issue currentCategory = null;
    for (String categoryName : split) {
      currentCategory = getIssueBySummary(categoryName, IssueType.CATEGORY, project);
      if (previousCategory != null && currentCategory.hierarchyLinks().isEmpty()) {
        AddIssueLink addIssueLink =
            AddIssueLink.createAddLink(IssueLinkType.HIERARCHY, currentCategory, previousCategory);
        linkIssues(addIssueLink);
      }
      previousCategory = currentCategory;
    }
    return currentCategory;
  }

}
