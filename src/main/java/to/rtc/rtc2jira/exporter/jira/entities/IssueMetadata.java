package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IssueMetadata {

  private String expand;
  private List<Project> projects;


  public String getExpand() {
    return expand;
  }

  public void setExpand(String expand) {
    this.expand = expand;
  }

  public List<Project> getProjects() {
    return projects;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }

  public Optional<Project> getProject(String projectKey) {
    for (Project project : projects) {
      if (project.getKey().equals(projectKey)) {
        return Optional.of(project);
      }
    }
    return Optional.empty();
  }

}
