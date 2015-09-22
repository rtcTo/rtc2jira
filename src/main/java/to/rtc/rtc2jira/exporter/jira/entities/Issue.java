package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonView;

@XmlRootElement
public class Issue extends BaseEntity {

  private String expand;
  private IssueFields fields;


  @JsonView(IssueView.Read.class)
  public String getExpand() {
    return expand;
  }

  public void setExpand(String expand) {
    this.expand = expand;
  }

  public IssueFields getFields() {
    if (fields == null) {
      fields = new IssueFields();
    }
    return fields;
  }

  public void setFields(IssueFields fields) {
    this.fields = fields;
  }

  @Override
  public String getPath() {
    return "/issue";
  }

  @Override
  public String getSelfPath() {
    String key = getKey() != null ? getKey() : getId();
    return getPath() + "/" + key;
  }

  @JsonView(IssueView.Filtered.class)
  public Date getCreated() {
    return getFields().getCreated();
  }

  public void setCreated(Date created) {
    getFields().setCreated(created);
  }


  /**
   * Should return collection with only one item
   * 
   * @return
   */
  public Set<IssueLink> categoryLinks() {
    Set<IssueLink> result = new HashSet<IssueLink>();
    Set<IssueLink> issuelinks = getFields().getIssuelinks();
    for (IssueLink issueLink : issuelinks) {
      issueLink.setReferencingIssue(this);
      if (issueLink.getType().equals(IssueLinkType.CATEGORY)) {
        result.add(issueLink);
      }
    }
    return result;
  }

  /**
   * Should return collection with only one item
   * 
   * @return
   */
  public Set<IssueLink> iterationLinks() {
    Set<IssueLink> result = new HashSet<IssueLink>();
    Set<IssueLink> issuelinks = getFields().getIssuelinks();
    for (IssueLink issueLink : issuelinks) {
      issueLink.setReferencingIssue(this);
      if (issueLink.getType().equals(IssueLinkType.ITERATION)) {
        result.add(issueLink);
      }
    }
    return result;
  }

  /**
   * Should return collection with only one item
   * 
   * @return
   */
  public Set<IssueLink> hierarchyLinks() {
    Set<IssueLink> result = new HashSet<IssueLink>();
    Set<IssueLink> issuelinks = getFields().getIssuelinks();
    for (IssueLink issueLink : issuelinks) {
      issueLink.setReferencingIssue(this);
      if (issueLink.getType().equals(IssueLinkType.HIERARCHY)) {
        result.add(issueLink);
      }
    }
    return result;
  }


  public Issue asReferenceObject() {
    Issue result = new Issue();
    result.setKey(this.getKey());
    return result;
  }

}
