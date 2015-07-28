package to.rtc.rtc2jira.mapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import to.rtc.rtc2jira.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.Comment;

import com.ibm.team.repository.client.IItemManager;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.client.internal.ItemManager;
import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.repository.common.model.Contributor;
import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.IComment;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class CommentMapping extends MappingAdapter {

  private List<IComment> value;
  private String identifier;

  public CommentMapping(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public void beforeWorkItem() {
    value = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    value = getValue(attribute);
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (value != null) {
      List<Comment> comments = new ArrayList<>();
      for (IComment rtcCom : value) {
        Contributor rtcCreator = null;
        try {
          IItemManager itemManager =
              ITeamRepository.class.cast(getWorkItem().getOrigin()).itemManager();
          rtcCreator =
              (Contributor) itemManager.fetchCompleteItem(rtcCom.getCreator(), ItemManager.DEFAULT,
                  null);
        } catch (TeamRepositoryException e) {
          e.printStackTrace();
        }
        String creatorEmail = rtcCreator.getEmailAddress();
        String creatorName = rtcCreator.getName();
        Date creationDate = new Date(rtcCom.getCreationDate().getTime());
        String plainTextComment = rtcCom.getHTMLContent().getPlainText();
        Comment comment = new Comment(creatorName, creatorEmail, creationDate, plainTextComment);
        comments.add(comment);
      }
      if (!comments.isEmpty()) {
        doc.field(identifier, comments);
      }
    }
  }

}
