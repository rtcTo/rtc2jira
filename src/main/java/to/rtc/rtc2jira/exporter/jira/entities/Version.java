package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.map.annotate.JsonView;

public class Version extends NamedEntity {

  String description;
  boolean archived = false;
  boolean released = true;
  Date releaseDate;
  String project;
  Integer projectId;


  @JsonView(IssueView.Filtered.class)
  @Override
  public String getKey() {
    return super.getKey();
  }

  @Override
  public String getPath() {
    return "/version";
  }

  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public boolean isArchived() {
    return archived;
  }


  public void setArchived(boolean archived) {
    this.archived = archived;
  }


  public boolean isReleased() {
    return released;
  }


  public void setReleased(boolean released) {
    this.released = released;
  }

  @XmlJavaTypeAdapter(JiraDateStringAdapter.class)
  public Date getReleaseDate() {
    return releaseDate;
  }


  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }


  @JsonView(IssueView.Create.class)
  public String getProject() {
    return project;
  }


  public void setProject(String project) {
    this.project = project;
  }


  public Integer getProjectId() {
    return projectId;
  }


  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }


}
