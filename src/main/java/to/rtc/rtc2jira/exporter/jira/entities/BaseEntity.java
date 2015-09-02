package to.rtc.rtc2jira.exporter.jira.entities;

import java.net.URL;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonView;

@XmlRootElement
public abstract class BaseEntity {

  private String id;
  private String key;
  private URL self;


  protected BaseEntity() {}

  protected BaseEntity(String id) {
    this.id = id;
  }

  @JsonView(IssueView.Read.class)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @JsonView(IssueView.Read.class)
  public URL getSelf() {
    return self;
  }

  public void setSelf(URL self) {
    this.self = self;
  }


  @JsonView(IssueView.Filtered.class)
  abstract public String getPath();

  @JsonView(IssueView.Filtered.class)
  abstract public String getSelfPath();

  @JsonView(IssueView.Read.class)
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

}
