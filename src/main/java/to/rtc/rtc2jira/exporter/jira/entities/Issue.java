package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonView;

@XmlRootElement
public class Issue extends BaseEntity {

  private String expand;
  private IssueFields fields;


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
    return getPath() + "/" + getKey();
  }

  @JsonView(IssueView.Filtered.class)
  public Date getCreated() {
    return getFields().getCreated();
  }

  public void setCreated(Date created) {
    getFields().setCreated(created);
  }


}
