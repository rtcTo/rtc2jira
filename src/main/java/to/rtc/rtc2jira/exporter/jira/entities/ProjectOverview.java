package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonView;

/**
 * Represents one object of a list, retrieved by resource /project/. <br>
 * 
 * @author Manuel
 */
@XmlRootElement()
@JsonIgnoreProperties({"avatarUrls"})
public class ProjectOverview extends NamedEntity {
  private String expand;
  private List<IssueType> issuetypes;

  // override JsonView
  @JsonView(IssueView.Create.class)
  public String getKey() {
    return super.getKey();
  }

  // override JsonView
  @JsonView(IssueView.Create.class)
  public String getId() {
    return super.getId();
  }

  @JsonView(IssueView.Filtered.class)
  @Override
  public Date getCreated() {
    return null;
  }

  @Override
  public void setCreated(Date created) {}

  @JsonView(IssueView.Read.class)
  public String getExpand() {
    return expand;
  }

  public void setExpand(String expand) {
    this.expand = expand;
  }

  /**
   * Only available when called rest method with expand="issuetypes"
   * 
   * @return {@link List} of {@link IssueType}
   */
  @JsonView(IssueView.Read.class)
  public List<IssueType> getIssuetypes() {
    return issuetypes;
  }

  public void setIssueTypes(List<IssueType> issuetypes) {
    this.issuetypes = issuetypes;
  }

  @Override
  public String getPath() {
    return "/project";
  }

  @Override
  public String getSelfPath() {
    return getPath() + "/" + getKey();
  }

}
