package to.rtc.rtc2jira.importer.mapping;

import java.sql.Timestamp;
import java.util.Date;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;

import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class TimestampMapping extends MappingAdapter {

  private Date value;
  private String fieldName;

  public TimestampMapping(String fieldName) {
    this.fieldName = fieldName;
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
      doc.field(fieldName, value);
    }
  }

}
