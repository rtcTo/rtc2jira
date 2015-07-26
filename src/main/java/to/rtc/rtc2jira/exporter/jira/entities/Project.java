package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Represents one project retrieved by /project/Id <br>
 * Contains more attributes than {@link ProjectOverview}
 * 
 * @author Manuel
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {
  private String id;
  private String name;


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


}
