package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BulkCreateEntry {


  UpdateCommand update;
  IssueFields fields;

  public BulkCreateEntry(IssueFields issueFields) {
    this.update = new UpdateCommand();
    this.fields = issueFields;
  }

  public UpdateCommand getUpdate() {
    return update;
  }

  public void setUpdate(UpdateCommand update) {
    this.update = update;
  }

  public IssueFields getFields() {
    return fields;
  }

  public void setFields(IssueFields fields) {
    this.fields = fields;
  }

}
