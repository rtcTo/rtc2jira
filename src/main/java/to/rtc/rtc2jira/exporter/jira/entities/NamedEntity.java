package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
abstract public class NamedEntity extends BaseEntity {

  private String name;


  public NamedEntity() {
    super();
  }

  protected NamedEntity(String id, String name) {
    super(id);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}
