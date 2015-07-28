package to.rtc.rtc2jira.mapping;

import java.sql.Timestamp;
import java.util.Date;

import to.rtc.rtc2jira.spi.MappingAdapter;

import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class DirectDateMapping extends MappingAdapter {

  private Date value;
  private String identifier;

  public DirectDateMapping(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public void beforeWorkItem() {
    value = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    Timestamp timestamp = getValue(attribute);
    value = new Date(timestamp.getTime());
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (value != null) {
      doc.field(identifier, value);
    }
  }

}
