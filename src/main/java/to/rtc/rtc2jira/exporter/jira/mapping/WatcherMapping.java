/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    List<JiraUser> watchersToAdd = new ArrayList<JiraUser>();
    for (String formattedStr : userList) {
      JiraUser jiraUser = getUser(formattedStr);
      watchersToAdd.add(jiraUser);
    }
    addWatchers(watchersToAdd, issue);
  }

  private JiraUser getUser(String contributorStr) {
    JiraUser jiraUser = ContributorMapping.stringToUser(contributorStr);
    return getUser(jiraUser);
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


}
