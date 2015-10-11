package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.exporter.jira.JiraExporter;
import to.rtc.rtc2jira.exporter.jira.JiraRestAccess;
import to.rtc.rtc2jira.exporter.jira.entities.JiraUser;

import com.sun.jersey.api.client.ClientResponse;

/**
 * @author gustaf.hansen
 *
 */
public abstract class BaseUserMapping implements Mapping {
  private static final Logger LOGGER = Logger.getLogger(BaseUserMapping.class.getName());

  private JiraRestAccess restAccess;

  BaseUserMapping() {
    super();
    restAccess = JiraExporter.INSTANCE.getRestAccess();
  }


  JiraUser getUser(JiraUser jiraUser) {
    if ("unassigned".equals(jiraUser.getName().toLowerCase()))
      return null;

    if (!JiraExporter.INSTANCE.getExistingUsers().contains(jiraUser.getEmailAddress())) {
      ClientResponse clientResponse = getRestAccess().get(jiraUser.getSelfPath());
      if (clientResponse.getStatus() == 200) {
        JiraExporter.INSTANCE.getExistingUsers().add(jiraUser.getEmailAddress());
      } else {
        jiraUser = createUser(jiraUser);
      }
    }
    return jiraUser;
  }


  JiraUser createUser(JiraUser jiraUser) {
    ClientResponse clientResponse = getRestAccess().post(jiraUser.getPath(), jiraUser);
    if (clientResponse.getStatus() == 201) {
      jiraUser = clientResponse.getEntity(JiraUser.class);
      JiraExporter.INSTANCE.getExistingUsers().add(jiraUser.getEmailAddress());
      JiraExporter.INSTANCE.onCreateUser(jiraUser);
    } else {
      LOGGER.log(Level.SEVERE, "Problems while creating user " + clientResponse.getEntity(String.class));
    }
    return jiraUser;
  }


  protected JiraRestAccess getRestAccess() {
    return restAccess;
  }


}
