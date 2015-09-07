/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.Map.Entry;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.JiraUser;
import to.rtc.rtc2jira.importer.mapping.ContributorMapping;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class ReporterMapping extends BaseUserMapping {

  @Override
  public void map(Entry<String, Object> attribute, Issue issue, StorageEngine storage) {
    String formattedStr = (String) attribute.getValue();
    JiraUser jiraUser = getUser(formattedStr);
    issue.getFields().setReporter(jiraUser);
  }

  private JiraUser getUser(String contributorStr) {
    JiraUser jiraUser = ContributorMapping.stringToUser(contributorStr);
    return getUser(jiraUser);
  }


}
