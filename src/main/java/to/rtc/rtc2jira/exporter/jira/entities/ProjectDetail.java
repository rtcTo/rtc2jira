package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Represents one project retrieved by /project/Id <br>
 * Contains more attributes than {@link Project}
 * 
 * @author Manuel
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDetail {
  private String id;
  private String name;
  private String description;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


}
