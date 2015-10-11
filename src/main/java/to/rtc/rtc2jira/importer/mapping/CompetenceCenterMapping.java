/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.importer.mapping;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.FieldNames;

import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.ILiteral;
import com.ibm.team.workitem.common.model.Identifier;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * @author gustaf.hansen
 *
 */
public class CompetenceCenterMapping extends MappingAdapter {

  private String value;

  @Override
  protected void beforeWorkItem() {
    value = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    Identifier<ILiteral> identifier = getValue(attribute);
    value = identifier.getStringIdentifier();
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (value != null && !value.isEmpty()) {
      doc.field(FieldNames.COMPETENCE_CENTER, value);
    }
  }
}
