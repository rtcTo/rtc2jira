package to.rtc.rtc2jira.storage;

/**
 * List of all field names of a workitem, stored in the db
 * 
 * @author Manuel
 */
public interface WorkItemFieldNames extends WorkItemConstants {

  static final String GITHUB_WORKITEM_LINK = "githubissuenumber";
  static final String JIRA_ID_LINK = "jiraid";
  static final String JIRA_KEY_LINK = "jiraKey";


}
