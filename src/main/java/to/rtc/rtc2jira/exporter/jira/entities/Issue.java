package to.rtc.rtc2jira.exporter.jira.entities;

import java.net.URL;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Issue {

  private String expand;
  private String id;
  private String key;
  private URL self;
  private IssueFields fields;


  public String getExpand() {
    return expand;
  }

  public void setExpand(String expand) {
    this.expand = expand;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public URL getSelf() {
    return self;
  }

  public void setSelf(URL self) {
    this.self = self;
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

}
