package to.rtc.rtc2jira.mapping;

import to.rtc.rtc2jira.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.WorkItemConstants;

import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.IPriority;
import com.ibm.team.workitem.common.model.Identifier;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class PriorityMapping extends MappingAdapter {

  private String value;

  @Override
  public void beforeWorkItem() {
    value = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    Identifier<IPriority> priorityIdentifier = getValue(attribute);
    value = priorityIdentifier.getStringIdentifier();
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (value != null) {
      doc.field(WorkItemConstants.PRIORITY, value);
    }
  }

}
