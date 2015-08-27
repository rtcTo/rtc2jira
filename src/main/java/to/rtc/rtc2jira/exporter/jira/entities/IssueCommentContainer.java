package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class IssueCommentContainer {

  private List<IssueComment> comments;

  public IssueCommentContainer() {
    comments = new ArrayList<IssueComment>();
  }

  public List<IssueComment> getComments() {
    return comments;
  }

}
