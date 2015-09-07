package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BulkCreateContainer {

  private List<BulkCreateEntry> issueUpdates;


  public BulkCreateContainer() {
    issueUpdates = new LinkedList<BulkCreateEntry>();
  }


  public List<BulkCreateEntry> getIssueUpdates() {
    return issueUpdates;
  }


  public void setIssueUpdates(List<BulkCreateEntry> issueUpdates) {
    this.issueUpdates = issueUpdates;
  }

}
