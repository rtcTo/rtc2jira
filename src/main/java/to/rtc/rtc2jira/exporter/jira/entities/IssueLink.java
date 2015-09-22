package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Referenced by issue which owns the links
 * 
 * @author gustaf.hansen
 *
 */
@XmlRootElement
public class IssueLink extends BaseEntity {

  static public IssueLink createInward(IssueLinkType type, Issue inwardIssue) {
    return new IssueLink(type, inwardIssue, null);
  }

  static public IssueLink createOutward(IssueLinkType type, Issue outwardIssue) {
    return new IssueLink(type, outwardIssue, null);
  }

  private IssueLinkType type;
  private Issue inwardIssue;
  private Issue outwardIssue;


  public IssueLink() {}

  public IssueLink(IssueLinkType type, Issue inwardIssue, Issue outwardIssue) {
    this.setType(type);
    this.inwardIssue = inwardIssue;
    this.outwardIssue = outwardIssue;
  }

  void setReferencingIssue(Issue referencer) {
    if (this.inwardIssue == null) {
      inwardIssue = referencer;
    } else if (outwardIssue == null) {
      outwardIssue = null;
    }
  }

  @Override
  public String getPath() {
    return "/issueLink";
  }

  public Issue getInwardIssue() {
    return inwardIssue;
  }

  public void setInwardIssue(Issue inwardIssue) {
    this.inwardIssue = inwardIssue;
  }

  public Issue getOutwardIssue() {
    return outwardIssue;
  }

  public void setOutwardIssue(Issue outwardIssue) {
    this.outwardIssue = outwardIssue;
  }

  public IssueLinkType getType() {
    return type;
  }

  public void setType(IssueLinkType type) {
    this.type = type;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    IssueLinkType type2 = this.getType();
    result = prime * result + (type2.getName() == null ? 0 : type2.getName().hashCode());
    Issue inwardIssue2 = this.getInwardIssue();
    result =
        prime * result + (inwardIssue2 == null || inwardIssue2.getKey() == null ? 0 : inwardIssue2.getKey().hashCode());
    Issue outwardIssue2 = this.getOutwardIssue();
    result =
        prime * result
            + (outwardIssue2 == null || outwardIssue2.getKey() == null ? 0 : outwardIssue2.getKey().hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof IssueLink)) {
      return false;
    }
    IssueLink other = (IssueLink) obj;
    return other.getType().getName().equals(this.getType().getName())
        && other.getInwardIssue().getKey().equals(this.getInwardIssue().getKey())
        && other.getOutwardIssue().getKey().equals(this.getOutwardIssue().getKey());
  }


}
