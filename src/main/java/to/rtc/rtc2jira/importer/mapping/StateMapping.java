/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.importer.mapping;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.WorkItemConstants;

import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * @author roman.schaller
 *
 */
public class StateMapping extends MappingAdapter {

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
      doc.field(WorkItemConstants.STATE, value);
    }
  }
}
