package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.JiraExporter;
import to.rtc.rtc2jira.exporter.jira.JiraRestAccess;
import to.rtc.rtc2jira.exporter.jira.JiraUserManager;
import to.rtc.rtc2jira.exporter.jira.entities.JiraUser;

/**
 * @author gustaf.hansen
 *
 */
public abstract class BaseUserMapping implements Mapping {

  private JiraRestAccess restAccess;

  BaseUserMapping() {
    super();
    restAccess = JiraExporter.INSTANCE.getRestAccess();
  }

  JiraUser getUser(JiraUser jiraUser) {
    return JiraUserManager.INSTANCE.getUser(jiraUser);
  }


  JiraUser createUser(JiraUser jiraUser) {
    return JiraUserManager.INSTANCE.createUser(jiraUser);
  }


  protected JiraRestAccess getRestAccess() {
    return restAccess;
  }


}
