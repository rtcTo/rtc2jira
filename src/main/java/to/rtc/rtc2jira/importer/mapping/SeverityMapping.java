package to.rtc.rtc2jira.importer.mapping;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.WorkItemConstants;

import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.ISeverity;
import com.ibm.team.workitem.common.model.Identifier;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class SeverityMapping extends MappingAdapter {

  private String value;

  @Override
  public void beforeWorkItem() {
    value = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    Identifier<ISeverity> severityIdentifier = getValue(attribute);
    value = severityIdentifier.getStringIdentifier();
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (value != null) {
      doc.field(WorkItemConstants.SEVERITY, value);
    }
  }

}
