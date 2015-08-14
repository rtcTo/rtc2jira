/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.importer.mapping;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.FieldNames;

import com.ibm.team.workitem.common.internal.model.Category;
import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.ICategoryHandle;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * @author roman.schaller
 *
 */
public class CategoryMapping extends MappingAdapter {

  private String value;

  @Override
  protected void beforeWorkItem() {
    value = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    ICategoryHandle categoryHandle = getValue(attribute);
    Category category = fetchCompleteItem(categoryHandle);
    value = category.getName();
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    doc.field(FieldNames.CATEGORY, value);
  }
}
