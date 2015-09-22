package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.HashMap;
import java.util.Map;

import to.rtc.rtc2jira.exporter.jira.JiraExporter;
import to.rtc.rtc2jira.exporter.jira.JiraRestAccess;
import to.rtc.rtc2jira.exporter.jira.entities.Group;

import com.sun.jersey.api.client.ClientResponse;

public class TeamAreaHandler {
  public static final TeamAreaHandler INSTANCE;


  private Map<String, Group> alreadyExistingGroups;

  private JiraRestAccess access;


  static {
    INSTANCE = new TeamAreaHandler(JiraExporter.INSTANCE.getRestAccess());
  }

  private TeamAreaHandler(JiraRestAccess access) {
    this.access = access;
    this.alreadyExistingGroups = new HashMap<String, Group>();
  }

  private Group createGroup(String name) {
    Group group = new Group();
    group.setName(name);
    ClientResponse get = access.get(group.getSelfPath());
    if (get.getStatus() == 200) {
      group = get.getEntity(Group.class);
    } else {
      ClientResponse post = access.post(group.getPath(), group);
      if (post.getStatus() == 201) {
        group = post.getEntity(Group.class);
      } else {
        group = null;
      }
    }
    return group;
  }

  Group getByName(String groupName) {
    Group result = alreadyExistingGroups.get(groupName);
    if (result == null) {
      result = createGroup(groupName);
      alreadyExistingGroups.put(groupName, result);
    }
    return result;
  }


}
