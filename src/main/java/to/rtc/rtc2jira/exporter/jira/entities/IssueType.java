package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonView;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueType extends NamedEntity {

  private String description;
  private boolean subtask;


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @JsonView(IssueView.Update.class)
  public boolean isSubtask() {
    return subtask;
  }

  public void setSubtask(boolean subtask) {
    this.subtask = subtask;
  }

  @Override
  public String getPath() {
    return "/issuetype";
  }

  @Override
  public String getSelfPath() {
    return "/issuetype/" + getId();
  }

}
