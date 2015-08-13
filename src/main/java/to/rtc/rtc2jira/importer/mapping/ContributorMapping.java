/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.importer.mapping;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;

import com.ibm.team.repository.common.model.Contributor;
import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Can handle all attributes pointing to a contributor.
 * 
 * @author roman.schaller
 *
 */
public class ContributorMapping extends MappingAdapter {

  private String value;
  private String localStorageIdentifier;

  public ContributorMapping(String localStorageIdentifier) {
    this.localStorageIdentifier = localStorageIdentifier;
  }

  @Override
  protected void beforeWorkItem() {
    value = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    Contributor contributor = fetchCompleteItem(getValue(attribute));
    value = String.format("%s <%s>", contributor.getName(), contributor.getEmailAddress());
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    doc.field(localStorageIdentifier, value);
  }
}
