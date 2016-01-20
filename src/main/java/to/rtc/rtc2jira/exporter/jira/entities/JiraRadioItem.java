package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JiraRadioItem {
  private String value;
  private String id = "";

  static public final JiraRadioItem YES_ARCHIVED = new JiraRadioItem(true, "10200");
  static public final JiraRadioItem YES_PO_PREPLANNING = new JiraRadioItem(true, "10205");
  static public final JiraRadioItem YES_ROADMAP = new JiraRadioItem(true, "10209");
  static public final JiraRadioItem NONE = new JiraRadioItem(false, "");

  public JiraRadioItem() {}

  JiraRadioItem(boolean archived, String id) {
    value = archived ? "Yes" : "None";
    setId(id);
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
