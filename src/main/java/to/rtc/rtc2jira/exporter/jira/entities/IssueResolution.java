package to.rtc.rtc2jira.exporter.jira.entities;

import org.codehaus.jackson.map.annotate.JsonView;

public class IssueResolution extends NamedEntity {


  public IssueResolution() {
    super();
  }

  public IssueResolution(ResolutionEnum value) {
    this.setId("" + value.getJiraId());
  }

  @JsonView(IssueView.Filtered.class)
  public ResolutionEnum getEnum() {
    return ResolutionEnum.fromJiraId(Integer.parseInt(getId()));
  }

  @Override
  public String getPath() {
    return "/resolution";
  }

  @Override
  public String getSelfPath() {
    return getPath() + "/" + getId();
  }

}
