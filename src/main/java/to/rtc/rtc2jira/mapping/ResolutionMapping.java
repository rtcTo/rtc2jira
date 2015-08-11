package to.rtc.rtc2jira.mapping;

import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

import to.rtc.rtc2jira.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.WorkItemConstants;

public class ResolutionMapping extends MappingAdapter {

  private String value;

  @Override
  protected void beforeWorkItem() {
    value = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    value = getValue(attribute);
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (value != null && !value.isEmpty()) {
      doc.field(WorkItemConstants.RESOLUTION, value);
    }
  }
}
