package to.rtc.rtc2jira.exporter.jira.entities;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IssueDto {

  private String expand;
  private String id;
  private String key;
  private URL self;
  private Map<String, Object> fields;


  public IssueDto(Issue issue) {
    expand = issue.getExpand();
    id = issue.getId();
    key = issue.getKey();
    self = issue.getSelf();
    fields = issue.getFields().toMap();
  }


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

  public Map<String, Object> getFields() {
    if (fields == null) {
      fields = new HashMap<String, Object>();
    }
    return fields;
  }

  public void setFields(Map<String, Object> fields) {
    this.fields = fields;
  }

}
