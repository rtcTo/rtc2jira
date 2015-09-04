package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonView;

/**
 * Represents one project retrieved by /project/Id <br>
 * 
 * @author Manuel
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends ProjectOverview {
  private String description;


  @JsonView(IssueView.Read.class)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }



}
