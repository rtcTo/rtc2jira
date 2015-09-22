/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.exporter.jira.entities.AddIssueLink;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueFields;
import to.rtc.rtc2jira.exporter.jira.entities.IssueLink;
import to.rtc.rtc2jira.exporter.jira.entities.IssueLinkType;
import to.rtc.rtc2jira.importer.mapping.TargetMapping;
import to.rtc.rtc2jira.importer.mapping.TargetMapping.IterationInfo;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class IterationMapping implements Mapping {
  static Logger LOGGER = Logger.getLogger(IterationMapping.class.getName());

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    IterationInfo iterInfo = new IterationInfo();
    if (value == null || TargetMapping.NO_ITERATION.equals(value)) {
      removeCurrentIteration(issue);
    } else {
      try {
        iterInfo.unmarshall((String) value);
        IssueFields fields = issue.getFields();
        IterationHandler iterationHandler = IterationHandler.INSTANCE;

        Issue iteration = iterationHandler.getIterationIssue(iterInfo, fields.getProject());
        AddIssueLink addIssueLink = AddIssueLink.createAddLink(IssueLinkType.ITERATION, issue, iteration);
        if (!issue.iterationLinks().contains(addIssueLink)) {
          removeCurrentIteration(issue);
          iterationHandler.linkIssues(addIssueLink);
        }
      } catch (IOException e) {
        LOGGER.log(Level.SEVERE, "Could not unmarshall Iteration json: " + e.getMessage());
      }
    }
  }

  private void removeCurrentIteration(Issue issue) {
    Set<IssueLink> iterationLinks = issue.iterationLinks();
    for (IssueLink issueLink : iterationLinks) {
      IterationHandler.INSTANCE.removeLink(issueLink);
    }
  }

}
