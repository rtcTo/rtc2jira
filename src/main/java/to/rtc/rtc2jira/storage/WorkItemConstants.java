/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.storage;


/**
 * Data from rtc workitems is stored in the db with this keys.
 * 
 * @author roman.schaller
 *
 */
public interface WorkItemConstants {
  public static final String ID = "ID";
  public static final String SUMMARY = "summary";
  public static final String DESCRIPTION = "description";
  public static final String WORK_ITEM_TYPE = "workItemType";
  public static final String ACCEPTANCE_CRITERIAS = "acceptanceCriterias";
  public static final String MODIFIED = "modified";
  public static final String CREATIONDATE = "creationDate";
  public static final String COMMENTS = "internalComments";
  public static final String PRIORITY = "priority";
  public static final String SEVERITY = "severity";
  public static final String OWNER = "owner";
  public static final String CREATOR = "creator";
  public static final String MODIFIED_BY = "modifiedBy";
  public static final String RESOLVER = "resolver";
  public static final String CATEGORY = "category";
  public static final String ARCHIVED = "archived";
  public static final String PROJECT_AREA = "projectArea";
  public static final String STORY_POINTS = "storyPoints";
}
