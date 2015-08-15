package to.rtc.rtc2jira.importer.mapping;

import java.util.Date;

import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.FieldNames;

public class ResolutionDateMapping extends MappingAdapter {

  private Date resolutionDate;

  @Override
  protected void beforeWorkItem() {
    resolutionDate = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    resolutionDate = getValue(attribute);
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (resolutionDate != null) {
      doc.field(FieldNames.RESOLUTION_DATE, resolutionDate);
    }
  }


}
