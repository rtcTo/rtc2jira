package to.rtc.rtc2jira.importer.mapping;

import java.util.List;
import java.util.logging.Logger;

import to.rtc.rtc2jira.importer.RTCImporter;
import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;

import com.ibm.team.workitem.common.model.IApprovalDescriptor;
import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class ApprovalDescriptorMapping extends MappingAdapter {
  private static final Logger LOGGER = Logger.getLogger(ApprovalDescriptorMapping.class.getName());
  static {
    LOGGER.addHandler(RTCImporter.DEFAULT_LOG_HANDLER);
  }

  @Override
  protected void beforeWorkItem() {}

  @Override
  public void acceptAttribute(IAttribute attribute) {
    List<IApprovalDescriptor> approvals = getValue(attribute);
    if (!approvals.isEmpty()) {
      LOGGER.warning("Approval descriptors are currently not supported.");
    }
  }

  @Override
  public void afterWorkItem(ODocument doc) {}
}
