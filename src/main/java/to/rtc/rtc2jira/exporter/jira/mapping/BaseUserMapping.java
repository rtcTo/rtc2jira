package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.jira.JiraRestAccess;
import to.rtc.rtc2jira.exporter.jira.entities.JiraUser;

import com.sun.jersey.api.client.ClientResponse;

/**
 * @author gustaf.hansen
 *
 */
public abstract class BaseUserMapping implements Mapping {
  private static final Logger LOGGER = Logger.getLogger(WorkItemTypeMapping.class.getName());

  Set<String> existingUserEmails = new HashSet<String>(500);

  private JiraRestAccess restAccess;

  BaseUserMapping() {
    super();
    Settings settings = Settings.getInstance();
    restAccess = new JiraRestAccess(settings.getJiraUrl(), settings.getJiraUser(), settings.getJiraPassword());
  }


  JiraUser getUser(JiraUser jiraUser) {
    if (!existingUserEmails.contains(jiraUser.getEmailAddress())) {
      ClientResponse clientResponse = restAccess.get(jiraUser.getSelfPath());
      if (clientResponse.getStatus() == 200) {
        existingUserEmails.add(jiraUser.getEmailAddress());
      } else {
        jiraUser = createUser(jiraUser);
      }
    }
    return jiraUser;
  }


  JiraUser createUser(JiraUser jiraUser) {
    ClientResponse clientResponse = restAccess.post(jiraUser.getPath(), jiraUser);
    if (clientResponse.getStatus() == 201) {
      jiraUser = clientResponse.getEntity(JiraUser.class);
      existingUserEmails.add(jiraUser.getEmailAddress());
    } else {
      LOGGER.log(Level.SEVERE, "Problems while creating user " + clientResponse.getEntity(String.class));
    }
    return jiraUser;
  }

}
