/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira;

import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Paths.get;

import java.io.IOException;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author roman.schaller
 *
 */
public class Settings {

  private static final String PROXY_HOST = "proxy.host";
  private static final String PROXY_PORT = "proxy.port";
  private static final String NON_PROXY_HOST = "non.proxy.hosts";

  private static final String RTC_URL = "rtc.url";
  private static final String RTC_USER = "rtc.user";
  private static final String RTC_PASSWORD = "rtc.password";
  private static final String RTC_PROJECTAREA = "rtc.projectarea";
  static final String RTC_WORKITEM_ID_RANGE = "rtc.workitemid.range";

  private static final String GITHUB_USER = "github.user";
  private static final String GITHUB_PASSWORD = "github.password";
  private static final String GITHUB_OAUTH_TOKEN = "github.token";
  private static final String GITHUB_REPONAME = "github.reponame";
  private static final String GITHUB_REPOOWNER = "github.repoowner";

  private static final String JIRA_USER = "jira.user";
  private static final String JIRA_PASSWORD = "jira.password";
  private static final String JIRA_URL = "jira.url";
  private static final String JIRA_PROJECTKEY = "jira.projectkey";
  private static final String JIRA_FORCE_UPDATE = "jira.forceupdate";

  private static final String JIRA_TRANSITION_TODO_INPROGRESS = "jira.transitions.todoToInprogress.id";
  private static final String JIRA_TRANSITION_TODO_DONE = "jira.transitions.todoToDone.id";

  private static final String JIRA_TRANSITION_INPROGRESS_TODO = "jira.transitions.inprogressToTodo.id";
  private static final String JIRA_TRANSITION_INPROGRESS_DONE = "jira.transitions.inprogressToDone.id";

  private static final String JIRA_TRANSITION_DONE_TODO = "jira.transitions.doneToTodo.id";
  private static final String JIRA_TRANSITION_DONE_INPROGRESS = "jira.transitions.doneToInprogress.id";

  private static final String JIRA_ISSUETYPE_CATEGORY_ID = "jira.issueType.category.id";
  private static final String JIRA_ISSUETYPE_ITERATION_ID = "jira.issueType.iteration.id";

  private static final String JIRA_LINKTYPE_HIERARCHY_ID = "jira.linkType.hierarchy.id";
  private static final String JIRA_LINKTYPE_CATEGORY_ID = "jira.linkType.category.id";
  private static final String JIRA_LINKTYPE_ITERATION_ID = "jira.linkType.iteration.id";


  private static final String SYSOUT_EXPORTER = "sysout.exporter";

  private static final String DRY_RUN_IMPORT = "dry.run.import";

  private static final Settings instance = new Settings();

  private Properties props;

  private Settings() {
    try {
      Properties newProps = new Properties();
      newProps.load(newBufferedReader(get("settings.properties")));
      setProperties(newProps);
    } catch (IOException e) {
      System.err.println("Please create your settings.properties out of the settings.properties.example");
      throw new RuntimeException(e);
    }
  }

  void setProperties(Properties props) {
    for (Entry<Object, Object> entry : props.entrySet()) {
      if (entry.getValue() != null) {
        entry.setValue(entry.getValue().toString().trim());
      }
    }
    this.props = props;
  }

  public static Settings getInstance() {
    return instance;
  }

  public boolean hasProxySettings() {
    return props.getProperty(PROXY_HOST) != null && props.getProperty(PROXY_PORT) != null;
  }

  public String getProxyHost() {
    return props.getProperty(PROXY_HOST);
  }

  public String getProxyPort() {
    return props.getProperty(PROXY_PORT);
  }

  public String getNonProxyHosts() {
    return props.getProperty(NON_PROXY_HOST);
  }

  public boolean hasRtcProperties() {
    return props.containsKey(RTC_USER) && props.containsKey(RTC_PASSWORD) && props.containsKey(RTC_URL)
        && props.containsKey(RTC_WORKITEM_ID_RANGE);
  }

  public String getRtcUrl() {
    return props.getProperty(RTC_URL);
  }

  public String getRtcUser() {
    return props.getProperty(RTC_USER);
  }

  public String getRtcPassword() {
    return props.getProperty(RTC_PASSWORD);
  }

  public String getRtcProjectarea() {
    return props.getProperty(RTC_PROJECTAREA);
  }

  public Collection<Integer> getRtcWorkItemRange() {
    String rangesString = props.getProperty(RTC_WORKITEM_ID_RANGE);
    String[] ranges = rangesString.split(",");
    IntStream intStream = IntStream.of();
    for (String range : ranges) {
      String[] splitted = range.split("\\.\\.");
      int from = Integer.parseInt(splitted[0].trim());
      if (splitted.length == 1) {
        intStream = IntStream.concat(intStream, IntStream.rangeClosed(from, from));
      } else {
        int to = Integer.parseInt(splitted[1].trim());
        intStream = IntStream.concat(intStream, IntStream.rangeClosed(from, to));
      }
    }
    return intStream.boxed().collect(Collectors.toList());
  }

  public boolean hasGithubProperties() {
    return props.containsKey(GITHUB_USER)//
        && props.containsKey(GITHUB_PASSWORD)//
        && props.containsKey(GITHUB_OAUTH_TOKEN)//
        && props.containsKey(GITHUB_REPONAME)//
        && props.containsKey(GITHUB_REPOOWNER);
  }

  public String getGithubUser() {
    return props.getProperty(GITHUB_USER);
  }

  public String getGithubPassword() {
    return props.getProperty(GITHUB_PASSWORD);
  }

  public String getGithubToken() {
    return props.getProperty(GITHUB_OAUTH_TOKEN);
  }

  public String getGithubRepoName() {
    return props.getProperty(GITHUB_REPONAME);
  }

  public String getGithubRepoOwner() {
    return props.getProperty(GITHUB_REPOOWNER);
  }


  public boolean hasJiraProperties() {
    return props.containsKey(JIRA_USER)//
        && props.containsKey(JIRA_PASSWORD)//
        && props.containsKey(JIRA_URL) //
        && props.containsKey(JIRA_PROJECTKEY) && props.containsKey(JIRA_FORCE_UPDATE);
  }

  public String getJiraUser() {
    return props.getProperty(JIRA_USER);
  }

  public String getJiraPassword() {
    return props.getProperty(JIRA_PASSWORD);
  }

  public String getJiraUrl() {
    return props.getProperty(JIRA_URL);
  }

  public String getJiraProjectKey() {
    return props.getProperty(JIRA_PROJECTKEY);
  }

  public boolean isForceUpdate() {
    return Boolean.parseBoolean(props.getProperty(JIRA_FORCE_UPDATE, Boolean.toString(false)));
  }

  public boolean isSystemOutExporterConfigured() {
    return Boolean.parseBoolean(props.getProperty(SYSOUT_EXPORTER));
  }

  public boolean isDryRunImport() {
    return Boolean.parseBoolean(props.getProperty(DRY_RUN_IMPORT));
  }

  public String getJiraTransitionTodoInprogress() {
    return props.getProperty(JIRA_TRANSITION_TODO_INPROGRESS);
  }

  public String getJiraTransitionTodoDone() {
    return props.getProperty(JIRA_TRANSITION_TODO_DONE);
  }

  public String getJiraTransitionInprogressTodo() {
    return props.getProperty(JIRA_TRANSITION_INPROGRESS_TODO);
  }

  public String getJiraTransitionInprogressDone() {
    return props.getProperty(JIRA_TRANSITION_INPROGRESS_DONE);
  }

  public String getJiraTransitionDoneTodo() {
    return props.getProperty(JIRA_TRANSITION_DONE_TODO);
  }

  public String getJiraTransitionDoneInprogress() {
    return props.getProperty(JIRA_TRANSITION_DONE_INPROGRESS);
  }

  public String getJiraIssuetypeCategoryId() {
    return props.getProperty(JIRA_ISSUETYPE_CATEGORY_ID);
  }

  public String getJiraIssuetypeIterationId() {
    return props.getProperty(JIRA_ISSUETYPE_ITERATION_ID);
  }

  public String getJiraLinktypeHierarchyId() {
    return props.getProperty(JIRA_LINKTYPE_HIERARCHY_ID);
  }

  public String getJiraLinktypeCategoryId() {
    return props.getProperty(JIRA_LINKTYPE_CATEGORY_ID);
  }

  public String getJiraLinktypeIterationId() {
    return props.getProperty(JIRA_LINKTYPE_ITERATION_ID);
  }

}
