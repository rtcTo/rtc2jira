package to.rtc.rtc2jira.importer.mapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.Comment;
import to.rtc.rtc2jira.storage.FieldNames;

import com.ibm.team.repository.common.model.Contributor;
import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.IComment;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class CommentMapping extends MappingAdapter {

  private List<IComment> value;

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
      // don't just overwrite existing comments, because they contain jira export history
      List<Comment> comments = doc.field(FieldNames.COMMENTS);
      if (comments == null) {
        comments = new ArrayList<>();
      }
      for (IComment rtcCom : value) {
        Contributor rtcCreator = fetchCompleteItem(rtcCom.getCreator());
        String creatorEmail = rtcCreator.getEmailAddress();
        String creatorName = rtcCreator.getName();
        Date creationDate = new Date(rtcCom.getCreationDate().getTime());
        String plainTextComment = rtcCom.getHTMLContent().getPlainText();
        Comment comment = new Comment(creatorName, creatorEmail, creationDate, plainTextComment);
        if (!comments.contains(comment)) {
          comments.add(comment);
        }
      }
      if (!comments.isEmpty()) {
        doc.field(FieldNames.COMMENTS, comments);
      }
    }
  }

}
