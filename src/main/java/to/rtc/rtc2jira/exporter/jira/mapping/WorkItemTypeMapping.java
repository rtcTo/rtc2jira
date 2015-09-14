/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import static to.rtc.rtc2jira.storage.WorkItemTypes.BUSINESSNEED;
import static to.rtc.rtc2jira.storage.WorkItemTypes.DEFECT;
import static to.rtc.rtc2jira.storage.WorkItemTypes.EPIC;
import static to.rtc.rtc2jira.storage.WorkItemTypes.STORY;
import static to.rtc.rtc2jira.storage.WorkItemTypes.TASK;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.jira.JiraRestAccess;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueFields;
import to.rtc.rtc2jira.exporter.jira.entities.IssueMetadata;
import to.rtc.rtc2jira.exporter.jira.entities.IssueType;
import to.rtc.rtc2jira.exporter.jira.entities.ProjectOverview;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author roman.schaller
 *
 */
public class WorkItemTypeMapping implements Mapping {
  private static final Logger LOGGER = Logger.getLogger(WorkItemTypeMapping.class.getName());

  private Map<String, List<IssueType>> existingIssueTypes;
  private JiraRestAccess restAccess;

  public WorkItemTypeMapping(JiraRestAccess restAccess) {
    this.restAccess = restAccess;
  }

  public WorkItemTypeMapping() {
    Settings settings = Settings.getInstance();
    restAccess = new JiraRestAccess(settings.getJiraUrl(), settings.getJiraUser(), settings.getJiraPassword());
  }

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    String workitemType = (String) value;
    IssueFields issueFields = issue.getFields();
    ProjectOverview project = issueFields.getProject();
    switch (workitemType) {
      case TASK:
        issueFields.setIssuetype(getIssueType("Task", project));
        break;
      case STORY:
        issueFields.setIssuetype(getIssueType("User Story", project));
        break;
      case EPIC:
        issueFields.setIssuetype(getIssueType("Epic", project));
        break;
      case BUSINESSNEED:
        issueFields.setIssuetype(getIssueType("Business Need", project));
        break;
      case DEFECT:
        issueFields.setIssuetype(getIssueType("Bug", project));
        break;
      default:
        LOGGER.warning("Cannot determine issuetype for unknown workitemType: " + workitemType);
        break;
    }

  }

  public IssueType getIssueType(String issuetypeName, ProjectOverview project) {
    String projectKey = project.getKey();
    if (existingIssueTypes == null) {
      IssueMetadata issueMetadata =
          restAccess.get("/issue/createmeta/?expand=projects.issuetypes.fields.", IssueMetadata.class);
      existingIssueTypes = new HashMap<>();
      existingIssueTypes.put(projectKey, issueMetadata.getProject(projectKey).get().getIssuetypes());
    }

    List<IssueType> issuesTypesByProject = existingIssueTypes.get(projectKey);

    Supplier<IssueType> createIssueTypeForName = () -> {
      return createIssueType(issuetypeName);
    };

    IssueType issueType = getIssueTypeByName(issuetypeName, issuesTypesByProject).orElseGet(createIssueTypeForName);

    if (!issuesTypesByProject.contains(issueType)) {
      issuesTypesByProject.add(issueType);
    }
    return issueType;
  }

  private IssueType createIssueType(String issuetypeName) {
    IssueType newIssueType = new IssueType();
    newIssueType.setName(issuetypeName);
    newIssueType = restAccess.post(newIssueType.getPath(), newIssueType, IssueType.class);
    return newIssueType;
  }

  private Optional<IssueType> getIssueTypeByName(String name, Collection<IssueType> types) {
    List<IssueType> filteredTypes =
        types.stream().filter(issuetype -> issuetype.getName().equals(name)).collect(Collectors.toList());
    if (filteredTypes.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(filteredTypes.get(0));
    }

  }


}
