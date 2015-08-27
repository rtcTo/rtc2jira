package to.rtc.rtc2jira.importer.mapping;

import java.util.Date;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;

import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class DateMapping extends MappingAdapter {

  private Date date;
  private String fieldName;

  public DateMapping(String fieldName) {
    this.fieldName = fieldName;
  }

  @Override
  protected void beforeWorkItem() {
    date = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    date = getValue(attribute);
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (date != null) {
      doc.field(fieldName, date);
    } else {
      doc.removeField(fieldName);
    }
  }


}
