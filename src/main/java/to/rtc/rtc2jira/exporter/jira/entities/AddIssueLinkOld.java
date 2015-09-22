package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AddIssueLinkOld {

  IssueLinkType type;
  Issue inwardIssue;
  Issue outwardIssue;
  LinkComment comment;


  public AddIssueLinkOld() {}

  public AddIssueLinkOld(IssueLinkType type, Issue inwardIssue, Issue outwardIssue, LinkComment comment) {
    super();
    Issue inwardIssue2 = new Issue();
    inwardIssue2.setKey(inwardIssue.getKey());
    Issue outwardIssue2 = new Issue();
    outwardIssue2.setKey(outwardIssue.getKey());
    this.type = type;
    this.inwardIssue = inwardIssue2;
    this.outwardIssue = outwardIssue2;
    this.comment = comment;
  }

  public IssueLinkType getType() {
    return type;
  }

  public void setType(IssueLinkType type) {
    this.type = type;
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

  public LinkComment getComment() {
    return comment;
  }

  public void setComment(LinkComment comment) {
    this.comment = comment;
  }

  @XmlRootElement
  public static class LinkComment {
    private String body;

    public LinkComment(String body) {
      super();
      this.setBody(body);
    }

    public String getBody() {
      return body;
    }

    public void setBody(String body) {
      this.body = body;
    }

  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (this.getType().getName() == null ? 0 : this.getType().getName().hashCode());
    result = prime * result + (this.getInwardIssue().getKey() == null ? 0 : this.getInwardIssue().getKey().hashCode());
    result =
        prime * result + (this.getOutwardIssue().getKey() == null ? 0 : this.getOutwardIssue().getKey().hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof AddIssueLinkOld)) {
      return false;
    }
    AddIssueLinkOld other = (AddIssueLinkOld) obj;
    return other.getType().getName().equals(this.getType().getName())
        && other.getInwardIssue().getKey().equals(this.getInwardIssue().getKey())
        && other.getOutwardIssue().getKey().equals(this.getOutwardIssue().getKey());
  }

}
