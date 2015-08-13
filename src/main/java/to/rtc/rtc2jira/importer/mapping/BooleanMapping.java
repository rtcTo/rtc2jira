/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.importer.mapping;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;

import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * used to map boolean values
 * 
 * @author roman.schaller
 *
 */
public class BooleanMapping extends MappingAdapter {

  private Boolean value;
  private String localStorageIdentifier;

  public BooleanMapping(String localStorageIdentifier) {
    this.localStorageIdentifier = localStorageIdentifier;
  }

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
    doc.field(localStorageIdentifier, value);
  }

}
