package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import to.rtc.rtc2jira.exporter.jira.JiraExporter;
import to.rtc.rtc2jira.exporter.jira.JiraRestAccess;
import to.rtc.rtc2jira.exporter.jira.entities.AddIssueLink;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.IssueLinkType;
import to.rtc.rtc2jira.exporter.jira.entities.IssueType;
import to.rtc.rtc2jira.exporter.jira.entities.Project;
import to.rtc.rtc2jira.exporter.jira.entities.Version;
import to.rtc.rtc2jira.importer.mapping.TargetMapping.IterationInfo;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class IterationHandler extends LinkHandler {
  public static final IterationHandler INSTANCE;
  private static final Logger LOGGER = Logger.getLogger(IterationHandler.class.getName());

  private Map<String, Version> knownVersions = null;
  private Map<String, Version> knownSprints = null;
  private List<Version> versions;

  static {
    INSTANCE = new IterationHandler(JiraExporter.INSTANCE.getRestAccess());
  }

  private IterationHandler(JiraRestAccess access) {
    super(access);
  }

  protected Version getVersion(IterationInfo iterationInfo, Project project) {

    if (knownVersions == null) {
      knownVersions = new HashMap<String, Version>();
      ClientResponse response = access.get(project.getSelfPath() + "/versions");
      if (response.getStatus() == 200) {
        versions = response.getEntity(new GenericType<List<Version>>() {});
        for (Version version : versions) {
          knownVersions.put(version.getName(), version);
        }
      } else {
        LOGGER.severe("Could not retrieve versions for project " + project.getName());
      }
    }

    Version result = knownVersions.get(iterationInfo.name);
    if (result == null) {
      Version version = new Version();
      version.setName(iterationInfo.name);
      version.setReleaseDate(iterationInfo.endDate);
      version.setProjectId(Integer.parseInt(project.getId()));
      if (System.currentTimeMillis() < iterationInfo.endDate.toInstant().toEpochMilli()) {
        version.setReleased(false);
      }
      ClientResponse post = access.post(version.getPath(), version);
      if (post.getStatus() == 201) {
        result = post.getEntity(Version.class);
        knownVersions.put(result.getName(), result);
      } else {
        LOGGER.severe("There was a problem while creating a new version named " + version.getName()
            + ". Response entity: " + post.getEntity(String.class));
      }
    }
    return result;
  }

  Issue getIterationIssue(IterationInfo iterInfo, Project project) {
    Issue iterationIssue = getIssueBySummary(iterInfo.name, IssueType.ITERATION, project);
    if (iterInfo.parent != null) {
      if (iterationIssue.hierarchyLinks().isEmpty()) {
        Issue parent = getIterationIssue(iterInfo.parent, project);
        AddIssueLink addIssueLink = AddIssueLink.createAddLink(IssueLinkType.HIERARCHY, iterationIssue, parent);
        linkIssues(addIssueLink);
      }
    }
    return iterationIssue;
  }

  String getIterationQName(IterationInfo iterInfo) {
    String result = iterInfo.name;
    if (iterInfo.parent != null) {
      iterInfo = iterInfo.parent;
      result = iterInfo.name + "/" + result;
    }
    return result;
  }


}
