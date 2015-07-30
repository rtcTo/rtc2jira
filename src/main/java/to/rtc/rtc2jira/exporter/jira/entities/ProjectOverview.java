package to.rtc.rtc2jira.exporter.jira.entities;

import java.net.URL;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Represents one object of /project/. <br>
 * It has less attributes than a single {@link Project} referenced by /project/ID
 * 
 * @author Manuel
 */
@XmlRootElement()
@JsonIgnoreProperties({"avatarUrls"})
public class ProjectOverview {
  private String expand;
  private URL self;
  private String id;
  private String key;
  private String name;


  public String getExpand() {
    return expand;
  }

  public void setExpand(String expand) {
    this.expand = expand;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
