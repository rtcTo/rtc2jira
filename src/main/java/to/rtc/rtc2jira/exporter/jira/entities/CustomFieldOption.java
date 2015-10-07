package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonView;

@XmlRootElement
public class CustomFieldOption extends BaseEntity {

  private String value;

  protected CustomFieldOption() {}

  public CustomFieldOption(String id) {
    super(id);
  }

  @Override
  public String getPath() {
    return "/customFieldOption";
  }

  @JsonView(IssueView.Update.class)
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
