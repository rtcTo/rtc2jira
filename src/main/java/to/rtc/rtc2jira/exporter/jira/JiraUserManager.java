package to.rtc.rtc2jira.exporter.jira;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.ExportManager;
import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.jira.entities.JiraUser;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class JiraUserManager {

  private static final String ALREADY = "bereits";

  public static final JiraUserManager INSTANCE = new JiraUserManager();

  public static final String BASIC_JIRA_USER_GROUP_NAME = "jira-software-users";

  static final Logger LOGGER = Logger.getLogger(JiraUserManager.class.getName());
  static {
    LOGGER.addHandler(ExportManager.DEFAULT_LOG_HANDLER);
  }

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
    deactivationExclusionList.add("leonard.chew@bison-group.com");
    // meadows
    deactivationExclusionList.add("christian.oetterli@bison-group.com");
    deactivationExclusionList.add("diethard.straka@bison-group.com");
    deactivationExclusionList.add("dorian.nyffeler@bison-group.com");
    deactivationExclusionList.add("florian.waibel@bison-group.com");
    deactivationExclusionList.add("franz.ehrler@bison-group.com");
    deactivationExclusionList.add("friendly.user@bison-group.com");
    deactivationExclusionList.add("hendrik.schaefer@bison-group.com");
    deactivationExclusionList.add("joachim.huber@bison-group.com");
    deactivationExclusionList.add("johannes.eickhold@bison-group.com");
    deactivationExclusionList.add("kurt.muff@bison-group.com");
    deactivationExclusionList.add("marco.poli@bison-group.com");
    deactivationExclusionList.add("matthias.bohlen@bison-group.com");
    deactivationExclusionList.add("maximilian.koegel@bison-group.com");
    deactivationExclusionList.add("michael.bruelisauer@bison-group.com");
    deactivationExclusionList.add("mustapha.bouaaoud@bison-group.com");
    deactivationExclusionList.add("patrick.reinhart@bison-group.com");
    deactivationExclusionList.add("roland.bucher@bison-group.com");
    deactivationExclusionList.add("leonard.chew@bison-group.com");
  }

  public void deactivateUsers() {

    ClientResponse response =
        getRestAccess().get("/user/assignable/search?project=" + Settings.getInstance().getJiraProjectKey());
    if (response.getStatus() == 200) {
      List<JiraUser> allUsers = response.getEntity(new GenericType<List<JiraUser>>() {});
      for (JiraUser jiraUser : allUsers) {
        String emailAddress = jiraUser.getEmailAddress();
        if (emailAddress != null && emailAddress.toLowerCase().contains("bison")) {
          existingUsers.add(emailAddress.toLowerCase());
        }
      }
    }

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
        getRestAccess().delete(
            "/group/user?groupname=" + BASIC_JIRA_USER_GROUP_NAME + "&username=" + jiraUser.getName());
    if (putResponse.getStatus() != 200) {
      LOGGER.log(Level.SEVERE, "Problems while removing user " + jiraUser.getEmailAddress()
          + "  from jira-users group. " + putResponse.getEntity(String.class));
    }
  }

  public void activateUser(JiraUser jiraUser) {
    jiraUser.setActive(true);
    // add user to jira-users group
    ClientResponse putResponse = getRestAccess().post("/group/user?groupname=" + BASIC_JIRA_USER_GROUP_NAME, jiraUser);
    if (putResponse.getStatus() != 201) {
      boolean logError = true;
      String errorEntityAsJson = putResponse.getEntity(String.class);
      if (putResponse.getStatus() == 400) {
        if (errorEntityAsJson.contains(ALREADY)) {
          logError = false;
        }
      }
      if (logError) {
        LOGGER.log(Level.WARNING, "Problems while adding user " + jiraUser.getEmailAddress() + "  to "
            + BASIC_JIRA_USER_GROUP_NAME + " group. " + errorEntityAsJson);
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
      LOGGER.log(Level.SEVERE,
          "Problems while creating user '" + jiraUser.getName() + "'" + clientResponse.getEntity(String.class));
    }
    return jiraUser;
  }



  private JiraRestAccess getRestAccess() {
    return JiraExporter.INSTANCE.getRestAccess();
  }



}
