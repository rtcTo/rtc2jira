package to.rtc.rtc2jira.exporter.jira.entities;

import org.codehaus.jackson.map.annotate.JsonView;

public class AddIssueLink extends IssueLink {

  static public AddIssueLink createAddLink(IssueLinkType type, Issue inwardIssue, Issue outwardIssue) {
    AddIssueLink result = new AddIssueLink();
    result.setType(type);
    result.setInwardIssue(inwardIssue);
    result.setOutwardIssue(outwardIssue);
    return result;
  }

  public AddIssueLink() {
    super();
  }

  @JsonView(IssueView.Filtered.class)
  @Override
  public String getId() {
    return super.getId();
  }

  @JsonView(IssueView.Filtered.class)
  @Override
  public String getKey() {
    return super.getKey();
  }

  @Override
  public void setOutwardIssue(Issue outwardIssue) {
    outwardIssue = outwardIssue.asReferenceObject();
    super.setOutwardIssue(outwardIssue);
  }

  @Override
  public void setInwardIssue(Issue inwardIssue) {
    inwardIssue = inwardIssue.asReferenceObject();
    super.setInwardIssue(inwardIssue);
  }

}
