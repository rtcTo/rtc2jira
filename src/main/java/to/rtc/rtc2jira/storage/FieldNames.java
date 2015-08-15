/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.storage;


/**
 * A list of all field names used in rtc2jira
 * 
 * @author roman.schaller
 *
 */
public interface FieldNames {
  String ID = "ID";
  String SUMMARY = "summary";
  String DESCRIPTION = "description";
  String WORK_ITEM_TYPE = "workItemType";
  String ACCEPTANCE_CRITERIAS = "acceptanceCriterias";
  String MODIFIED = "modified";
  String CREATIONDATE = "creationDate";
  String COMMENTS = "internalComments";
  String PRIORITY = "priority";
  String SEVERITY = "severity";
  String OWNER = "owner";
  String CREATOR = "creator";
  String MODIFIED_BY = "modifiedBy";
  String RESOLVER = "resolver";
  String RESOLUTION = "resolution";
  String CATEGORY = "category";
  String ARCHIVED = "archived";
  String PROJECT_AREA = "projectArea";
  String STORY_POINTS = "storyPoints";
  String CUSTOM_ATTRIBUTES = "customAttributes";
  String STATE = "state";
  String TAGS = "tags";
  String ITERATION_LABEL = "iterationLabel";
  String GITHUB_WORKITEM_LINK = "githubissuenumber";
  String JIRA_ID_LINK = "jiraid";
  String JIRA_KEY_LINK = "jiraKey";
  String RESOLUTION_DATE = "resolutionDate";

}
