/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.mapping;

import to.rtc.rtc2jira.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.WorkItemConstants;

import com.ibm.team.process.internal.common.Iteration;
import com.ibm.team.process.internal.common.IterationHandle;
import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * @author roman.schaller
 *
 */
public class TargetMapping extends MappingAdapter {

  private String iterationLabel;

  @Override
  protected void beforeWorkItem() {
    iterationLabel = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    IterationHandle handle = getValue(attribute);
    if (handle != null) {
      Iteration iteration = fetchCompleteItem(handle);
      iterationLabel = iteration.getLabel();
    }
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (iterationLabel != null && !iterationLabel.isEmpty()) {
      doc.field(WorkItemConstants.ITERATION_LABEL, iterationLabel);
    }
  }

}
