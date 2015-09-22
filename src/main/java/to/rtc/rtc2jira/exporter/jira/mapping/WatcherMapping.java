/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlRootElement;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.JiraUser;
import to.rtc.rtc2jira.importer.mapping.ContributorMapping;
import to.rtc.rtc2jira.storage.StorageEngine;

import com.sun.jersey.api.client.ClientResponse;

/**
 * @author gustaf.hansen
 *
 */
public class WatcherMapping extends BaseUserMapping {
  static final Logger LOGGER = Logger.getLogger(WatcherMapping.class.getName());


  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    @SuppressWarnings("unchecked")
    List<String> userList = (List<String>) value;
    List<JiraUser> existingWatchers = new ArrayList<JiraUser>();
    List<JiraUser> watchersToDelete = new ArrayList<JiraUser>();
    Watchers response = getExistingWatchers(issue);
    if (response != null) {
      existingWatchers = response.getWatchers();
    }
    List<JiraUser> watchersToAdd = new ArrayList<JiraUser>();
    for (String formattedStr : userList) {
      JiraUser jiraUser = getUser(formattedStr);
      watchersToAdd.add(jiraUser);
    }
    for (JiraUser jiraUser : existingWatchers) {
      if (!watchersToAdd.contains(jiraUser)) {
        watchersToDelete.add(jiraUser);
      }
    }
    removeWatchers(watchersToDelete, issue);
    addWatchers(watchersToAdd, issue);
  }

  private JiraUser getUser(String contributorStr) {
    JiraUser jiraUser = ContributorMapping.stringToUser(contributorStr);
    return getUser(jiraUser);
  }

  private Watchers getExistingWatchers(Issue issue) {
    ClientResponse clientResponse = getRestAccess().get(issue.getSelfPath() + "/watchers");
    if (clientResponse.getStatus() == 200) {
      return clientResponse.getEntity(Watchers.class);
    } else {
      LOGGER.log(Level.SEVERE, "Could not retrieve watchers for issue. " + clientResponse.getEntity(String.class));
      return null;
    }
  }

  private void addWatchers(List<JiraUser> watchers, Issue issue) {
    for (JiraUser watcher : watchers) {
      ClientResponse clientResponse =
          getRestAccess().post(issue.getSelfPath() + "/watchers", "\"" + watcher.getName() + "\"");
      if (clientResponse.getStatus() != 204) {
        LOGGER.log(Level.SEVERE, "Could not add watcher to issue. " + clientResponse.getEntity(String.class));
      }
    }
  }

  private void removeWatchers(List<JiraUser> watchers, Issue issue) {
    for (JiraUser watcher : watchers) {
      ClientResponse clientResponse =
          getRestAccess().delete(issue.getSelfPath() + "/watchers?username=" + watcher.getName());
      if (clientResponse.getStatus() != 204) {
        LOGGER.log(Level.SEVERE, "Could not delete watcher of issue. " + clientResponse.getEntity(String.class));
      }
    }
  }


  @XmlRootElement
  static class Watchers {
    boolean isWatching;
    int watchCount;
    List<JiraUser> watchers;

    public boolean isWatching() {
      return isWatching;
    }

    public void setWatching(boolean isWatching) {
      this.isWatching = isWatching;
    }

    public int getWatchCount() {
      return watchCount;
    }

    public void setWatchCount(int watchCount) {
      this.watchCount = watchCount;
    }

    public List<JiraUser> getWatchers() {
      return watchers;
    }

    public void setWatchers(List<JiraUser> watchers) {
      this.watchers = watchers;
    }


  }

}
