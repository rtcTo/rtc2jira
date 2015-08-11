/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.mapping;

import java.util.List;

import to.rtc.rtc2jira.spi.MappingAdapter;

import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.IAttributeHandle;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * @author roman.schaller
 *
 */
public class CustomAttributeMapping extends MappingAdapter {

  @Override
  protected void beforeWorkItem() {}

  @Override
  public void acceptAttribute(IAttribute attribute) {
    List<IAttributeHandle> attributeHandles = getValue(attribute);
    for (IAttributeHandle attributeHandle : attributeHandles) {
      IAttribute a = fetchCompleteItem(attributeHandle);
      System.out.println("Got custom attribute " + a.getDisplayName());
    }
  }

  @Override
  public void afterWorkItem(ODocument doc) {}
}
