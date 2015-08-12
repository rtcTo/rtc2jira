/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.mapping;

import to.rtc.rtc2jira.spi.MappingAdapter;

import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Use this mapping if you really want to ignore an RTC attribute.
 * 
 * @author roman.schaller
 *
 */
public class NullMapping extends MappingAdapter {

  // Does nothing. Just ignore the attribute.

  @Override
  public void acceptAttribute(IAttribute attribute) {}

  @Override
  public void afterWorkItem(ODocument doc) {}

  @Override
  protected void beforeWorkItem() {}


}
