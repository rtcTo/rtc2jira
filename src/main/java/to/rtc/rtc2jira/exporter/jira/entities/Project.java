package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Represents one project retrieved by /project/Id <br>
 * 
 * @author Manuel
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends ProjectOverview {
  private String description;


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * A {@link List} of {@link IssueType}
   */
  @Override
  public List<IssueType> getIssuetypes() {
    return super.getIssuetypes();
  }


}
