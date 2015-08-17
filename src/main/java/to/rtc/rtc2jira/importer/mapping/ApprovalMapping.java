package to.rtc.rtc2jira.importer.mapping;

import java.util.List;
import java.util.logging.Logger;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;

import com.ibm.team.workitem.common.model.IApproval;
import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class ApprovalMapping extends MappingAdapter {
  private static final Logger LOGGER = Logger.getLogger(ApprovalMapping.class.getName());

  @Override
  protected void beforeWorkItem() {}

  @Override
  public void acceptAttribute(IAttribute attribute) {
    List<IApproval> approvals = getValue(attribute);
    if (!approvals.isEmpty()) {
      LOGGER.warning("Approvals are currently not supported.");
    }
  }

  @Override
  public void afterWorkItem(ODocument doc) {}
}
