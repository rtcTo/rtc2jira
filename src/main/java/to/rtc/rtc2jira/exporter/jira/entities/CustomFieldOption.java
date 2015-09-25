package to.rtc.rtc2jira.exporter.jira.entities;

import org.codehaus.jackson.map.annotate.JsonView;

public class CustomFieldOption extends BaseEntity {

  private String value;

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
