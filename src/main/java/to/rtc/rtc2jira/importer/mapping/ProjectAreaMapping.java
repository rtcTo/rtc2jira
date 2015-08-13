/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.importer.mapping;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.WorkItemConstants;

import com.ibm.team.process.common.IProjectArea;
import com.ibm.team.process.common.IProjectAreaHandle;
import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Mapping for the project area attribute
 * 
 * @author roman.schaller
 *
 */
public class ProjectAreaMapping extends MappingAdapter {

  private String value;

  @Override
  protected void beforeWorkItem() {
    value = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    IProjectAreaHandle handle = getValue(attribute);
    IProjectArea projectArea = fetchCompleteItem(handle);
    value = projectArea.getName();
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    doc.field(WorkItemConstants.PROJECT_AREA, value);
  }
}
