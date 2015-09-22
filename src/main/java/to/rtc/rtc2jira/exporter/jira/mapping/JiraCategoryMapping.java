/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.Set;
import java.util.regex.Pattern;

import to.rtc.rtc2jira.exporter.jira.entities.AddIssueLink;
import to.rtc.rtc2jira.exporter.jira.entities.Group;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueFields;
import to.rtc.rtc2jira.exporter.jira.entities.IssueLink;
import to.rtc.rtc2jira.exporter.jira.entities.IssueLinkType;
import to.rtc.rtc2jira.importer.mapping.CategoryMapping;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class JiraCategoryMapping implements Mapping {

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    String composedValue = (String) value;
    String[] segs = composedValue.split(Pattern.quote(CategoryMapping.FIELD_SEPARATOR));
    String categoryQualifiedName = segs[0];

    IssueFields fields = issue.getFields();
    TeamAreaHandler teamAreaHandler = TeamAreaHandler.INSTANCE;
    CategoryHandler categoryHandler = CategoryHandler.INSTANCE;

    if (!CategoryMapping.NO_CATEGORY.equals(categoryQualifiedName)) {
      Issue category =
          categoryHandler.getCategoryByQualifiedName(categoryQualifiedName, issue.getFields().getProject());
      AddIssueLink addIssueLink = AddIssueLink.createAddLink(IssueLinkType.CATEGORY, issue, category);
      if (!issue.categoryLinks().contains(addIssueLink)) {
        removeCurrentCategory(issue);
        categoryHandler.linkIssues(addIssueLink);
      }
    } else {
      removeCurrentCategory(issue);
    }

    if (!CategoryMapping.NO_TEAM.equals(segs[1])) {
      Group teamArea = teamAreaHandler.getByName(segs[1]);
      fields.setTeam(teamArea);
    } else {
      fields.setTeam(null);
    }
  }

  private void removeCurrentCategory(Issue issue) {
    Set<IssueLink> categoryLinks = issue.categoryLinks();
    for (IssueLink issueLink : categoryLinks) {
      CategoryHandler.INSTANCE.removeLink(issueLink);
    }
  }


}
