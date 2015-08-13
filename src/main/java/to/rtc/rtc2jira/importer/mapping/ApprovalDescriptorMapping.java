package to.rtc.rtc2jira.importer.mapping;

import java.util.List;

import com.ibm.team.workitem.common.model.IApprovalDescriptor;
import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;

public class ApprovalDescriptorMapping extends MappingAdapter {

  @Override
  protected void beforeWorkItem() {}

  @Override
  public void acceptAttribute(IAttribute attribute) {
    List<IApprovalDescriptor> approvals = getValue(attribute);
    if (!approvals.isEmpty()) {
      System.out.println("Approval descriptors are currently not supported.");
    }
  }

  @Override
  public void afterWorkItem(ODocument doc) {}
}
