package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Issue extends BaseEntity {

  private String expand;
  private String key;
  private IssueFields fields;


  public String getExpand() {
    return expand;
  }

  public void setExpand(String expand) {
    this.expand = expand;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
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


}
