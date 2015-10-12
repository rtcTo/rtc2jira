/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.importer.mapping;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.FieldNames;

import com.ibm.team.workitem.common.internal.util.SeparatedStringList;
import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * @author roman.schaller
 *
 */
public class IndustrySectorMapping extends MappingAdapter {

  private SeparatedStringList tags;

  @Override
  protected void beforeWorkItem() {
    tags = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    tags = getValue(attribute);
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (tags != null && tags.size() > 0) {
      doc.field(FieldNames.INDUSTRY_SECTOR, tags);
    }
  }
}
