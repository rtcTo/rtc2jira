package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonView;

import to.rtc.rtc2jira.Settings;

@XmlRootElement
public class IssueLinkType extends NamedEntity {

  public final static IssueLinkType BLOCKS;
  public final static IssueLinkType CLONERS;
  public final static IssueLinkType DUPLICATE;
  public final static IssueLinkType RELATES;
  public final static IssueLinkType HIERARCHY;
  public final static IssueLinkType CATEGORY;
  public final static IssueLinkType ITERATION;

  static {
    BLOCKS = new IssueLinkType("10000", "Blocks");
    CLONERS = new IssueLinkType("10001", "Cloners");
    DUPLICATE = new IssueLinkType("10002", "Duplicate");
    RELATES = new IssueLinkType("10003", "Relates");
    HIERARCHY = new IssueLinkType(Settings.getInstance().getJiraLinktypeHierarchyId(), "Hierarchy");
    CATEGORY = new IssueLinkType(Settings.getInstance().getJiraLinktypeCategoryId(), "Category");
    ITERATION = new IssueLinkType(Settings.getInstance().getJiraLinktypeIterationId(), "Iteration");
  }

  String inward;
  String outward;

  public IssueLinkType() {}

  public IssueLinkType(String id, String name) {
    super(id, name);
  }

  @JsonView(IssueView.Update.class)
  @Override
  public String getKey() {
    // TODO Auto-generated method stub
    return super.getKey();
  }

  @Override
  public String getPath() {
    return "/issueLinkType";
  }

  public String getInward() {
    return inward;
  }

  public void setInward(String inward) {
    this.inward = inward;
  }

  public String getOutward() {
    return outward;
  }

  public void setOutward(String outward) {
    this.outward = outward;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    return prime * result + getName().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof IssueLinkType)) {
      return false;
    }
    IssueLinkType other = (IssueLinkType) obj;
    return other.getName().equals(this.getName());
  }

}
