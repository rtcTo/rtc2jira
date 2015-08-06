/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.storage;


/**
 * Data from default mappings are stored in the db with this keys.
 * 
 * @author roman.schaller
 *
 */
public interface WorkItemConstants {
  public static final String ID = "ID";
  public static final String SUMMARY = "summary";
  public static final String DESCRIPTION = "description";
  public static final String WORK_ITEM_TYPE = "workItemType";
  public static final String ACCEPTANCE_CRITERIAS = "com.ibm.team.apt.attribute.acceptance";
  public static final String MODIFIED = "modified";
  public static final String CREATIONDATE = "creationDate";
  public static final String COMMENTS = "internalComments";
  public static final String PRIORITY = "priority";
  public static final String SEVERITY = "severity";
  public static final String OWNER = "owner";
}
