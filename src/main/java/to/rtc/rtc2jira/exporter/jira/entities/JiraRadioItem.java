package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JiraRadioItem {
  private String value;

  static public final JiraRadioItem YES = new JiraRadioItem(true);
  static public final JiraRadioItem NONE = new JiraRadioItem(false);

  JiraRadioItem(boolean archived) {
    value = archived ? "Yes" : "None";
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
