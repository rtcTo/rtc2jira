/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.List;
import java.util.logging.Logger;

import to.rtc.rtc2jira.exporter.jira.JiraExporter;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueFields;
import to.rtc.rtc2jira.exporter.jira.entities.IssueType;
import to.rtc.rtc2jira.storage.StorageEngine;
import to.rtc.rtc2jira.storage.WorkItemTypes;

/**
 * @author roman.schaller
 *
 */
public class ParentMapping implements Mapping {
  public static Logger LOGGER = Logger.getLogger(ParentMapping.class.getName());


  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    if (value != null) {
      @SuppressWarnings("unchecked")
      List<String> parentInfo = (List<String>) value;
      if (parentInfo.size() == 2) {
        String rtcId = parentInfo.get(0);
        String parentKey = JiraExporter.INSTANCE.getIssueKey(rtcId);
        String workItemType = parentInfo.get(1);

        switch (workItemType) {
          case WorkItemTypes.EPIC:
            handleEpicParent(parentKey, issue);
            break;
          case WorkItemTypes.STORY:
            handleStoryParent(parentKey, issue);
            break;
          default:
            handleGenericParentChild(parentKey, issue);
            break;
        }
      }
    }
  }


  private void handleGenericParentChild(String parentKey, Issue childIssue) {
    Issue parent = new Issue();
    parent.setKey(parentKey);
    ParentChildHandler.INSTANCE.addParentChildLink(childIssue, parent);

    // clean up epic link
    handleEpicLinkRemoval(childIssue);
  }


  private void handleEpicLinkRemoval(Issue childIssue) {
    IssueFields fields = childIssue.getFields();
    String curEpicLink = fields.getEpicLink();
    if (curEpicLink != null) {
      fields.setEpicLink(null);
      LOGGER.warning("The epic link of issue '" + childIssue.getKey() + "' has been removed");
    }
  }

  private void handleEpicParent(String parentKey, Issue childIssue) {
    IssueFields fields = childIssue.getFields();
    String curEpicLink = fields.getEpicLink();
    if (curEpicLink != null) {
      if (curEpicLink.equals(parentKey)) {
        // do not re-add same link: causes server error
        fields.setEpicLink(null);
      } else {
        // do not change link: causes server error
        fields.setEpicLink(null);
        LOGGER.warning("The epic link of issue '" + childIssue.getKey() + "' has been changed from '" + curEpicLink
            + "' to '" + parentKey + "'");
        // setEpicLink(parentKey, fields);
      }
    } else {
      setEpicLink(parentKey, fields);
    }
  }

  private void setEpicLink(String parentKey, IssueFields fields) {
    try {
      JiraExporter.INSTANCE.updateIfStillDummy(parentKey);
      fields.setEpicLink(parentKey);
    } catch (Exception e) {
      LOGGER.severe("A problem occurred while updating the epic link issue " + parentKey);
    }
  }

  private void handleStoryParent(String parentKey, Issue childIssue) {
    Issue parentStory = new Issue();
    parentStory.setKey(parentKey);
    if (childIssue.getFields().getIssuetype().equals(IssueType.TASK)) {
      StoryTaskHandler.INSTANCE.addTaskToStory(childIssue, parentStory);
    }

    // clean up epic link
    handleEpicLinkRemoval(childIssue);
  }

}
