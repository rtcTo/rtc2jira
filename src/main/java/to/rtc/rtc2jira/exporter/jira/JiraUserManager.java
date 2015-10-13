package to.rtc.rtc2jira.exporter.jira;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.exporter.jira.entities.JiraUser;

import com.sun.jersey.api.client.ClientResponse;

public class JiraUserManager {

  public static final JiraUserManager INSTANCE = new JiraUserManager();

  static final Logger LOGGER = Logger.getLogger(JiraUserManager.class.getName());

  private Set<String> createdUsers = new HashSet<String>(500);
  private Set<String> existingUsers = new HashSet<String>(500);
  private Set<String> deactivationExclusionList = new HashSet<String>(15);


  public JiraUserManager() {
    deactivationExclusionList.add("gustaf.hansen@bison-group.com");
    deactivationExclusionList.add("manuel.wessner@bison-group.com");
    deactivationExclusionList.add("martin.ziswiler@bison-group.com");
    deactivationExclusionList.add("otmar.humbel@bison-group.com");
    deactivationExclusionList.add("roman.schaller@gmail.com");
    deactivationExclusionList.add("noreply@atlassian.com");
    deactivationExclusionList.add("urs.haller@bison-group.com");
    deactivationExclusionList.add("pedraita.dayana@bison-group.com");
  }

  public void deactivateUsers() {
    for (String email : existingUsers) {
      if (!deactivationExclusionList.contains(email)) {
        JiraUser jiraUser = new JiraUser();
        jiraUser.setEmailAddress(email);
        String name = email.split("@")[0];
        jiraUser.setName(name);
        jiraUser.setKey(name);
        jiraUser.setActive(false);
        deactivateUser(jiraUser);
      }
    }
  }

  public void deactivateUser(JiraUser jiraUser) {
    jiraUser.setActive(false);
    ClientResponse putResponse =
        getRestAccess().delete("/group/user?groupname=jira-users&username=" + jiraUser.getName());
    if (putResponse.getStatus() != 200) {
      LOGGER.log(Level.SEVERE, "Problems while removing user " + jiraUser.getEmailAddress()
          + "  from jira-users group. " + putResponse.getEntity(String.class));
    }
  }

  public void activateUser(JiraUser jiraUser) {
    jiraUser.setActive(true);
    ClientResponse putResponse = getRestAccess().post("/group/user?groupname=jira-users", jiraUser);
    if (putResponse.getStatus() != 201) {
      boolean logError = true;
      String errorEntityAsJson = putResponse.getEntity(String.class);
      if (putResponse.getStatus() == 400) {
        if (errorEntityAsJson.contains("already")) {
          logError = false;
        }
      }
      if (logError) {
        LOGGER.log(Level.SEVERE, "Problems while adding user " + jiraUser.getEmailAddress() + "  to jira-users group. "
            + errorEntityAsJson);
      }
    }
  }


  public JiraUser getUser(JiraUser jiraUser) {
    if ("unassigned".equals(jiraUser.getName().toLowerCase()))
      return null;

    if (!existingUsers.contains(jiraUser.getEmailAddress())) {
      ClientResponse clientResponse = getRestAccess().get(jiraUser.getSelfPath());
      if (clientResponse.getStatus() == 200) {
        activateUser(jiraUser);
        existingUsers.add(jiraUser.getEmailAddress());
      } else {
        jiraUser = createUser(jiraUser);
      }
    }
    return jiraUser;
  }

  public JiraUser createUser(JiraUser jiraUser) {
    ClientResponse clientResponse = getRestAccess().post(jiraUser.getPath(), jiraUser);
    if (clientResponse.getStatus() == 201) {
      jiraUser = clientResponse.getEntity(JiraUser.class);
      existingUsers.add(jiraUser.getEmailAddress());
      createdUsers.add(jiraUser.getEmailAddress());
    } else {
      LOGGER.log(Level.SEVERE, "Problems while creating user " + clientResponse.getEntity(String.class));
    }
    return jiraUser;
  }



  private JiraRestAccess getRestAccess() {
    return JiraExporter.INSTANCE.getRestAccess();
  }



}
