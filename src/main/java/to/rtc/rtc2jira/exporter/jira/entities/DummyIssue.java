package to.rtc.rtc2jira.exporter.jira.entities;

import java.net.URL;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DummyIssue {

  private String expand;
  private String id;
  private String key;
  private URL self;
  private DummyIssueFields fields;


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

  public DummyIssueFields getFields() {
    if (fields == null) {
      fields = new DummyIssueFields();
    }
    return fields;
  }

  public void setFields(DummyIssueFields fields) {
    this.fields = fields;
  }

}
