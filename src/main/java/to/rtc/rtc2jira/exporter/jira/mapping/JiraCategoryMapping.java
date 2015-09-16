/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.regex.Pattern;

import to.rtc.rtc2jira.exporter.jira.entities.Group;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueFields;
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
    String categoryName = "_" + segs[0];
    IssueFields fields = issue.getFields();
    TeamAreaHandler teamAreaHandler = TeamAreaHandler.INSTANCE;
    Group category = teamAreaHandler.getByName(categoryName);
    fields.setFiledAgainst(category);
    if (!CategoryMapping.NO_TEAM.equals(segs[1])) {
      Group teamArea = teamAreaHandler.getByName(segs[1]);
      fields.setTeam(teamArea);
    }
  }
}
