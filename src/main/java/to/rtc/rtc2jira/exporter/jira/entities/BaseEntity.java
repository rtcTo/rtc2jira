package to.rtc.rtc2jira.exporter.jira.entities;

import java.net.URL;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class BaseEntity {

  private String id;
  private String key;
  private URL self;


  protected BaseEntity() {}

  protected BaseEntity(String id) {
    this.id = id;
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


  abstract public String getPath();

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

}
