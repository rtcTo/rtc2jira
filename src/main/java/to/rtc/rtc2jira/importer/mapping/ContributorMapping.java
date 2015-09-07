/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.importer.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.JiraUser;
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
    value = contributorToString(contributor);
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    doc.field(localStorageIdentifier, value);
  }


  public static String contributorToString(Contributor contributor) {
    return String.format("%s <%s>", contributor.getName(), contributor.getEmailAddress());
  }

  public static JiraUser stringToUser(String str) {
    str = str.substring(0, str.length() - 1);
    String[] split = str.split(" <");
    JiraUser jiraUser = new JiraUser();
    jiraUser.setDisplayName(split[0]);
    jiraUser.setEmailAddress(split[1]);
    String[] segs = split[1].toLowerCase().split("@");
    jiraUser.setKey(segs[0]);
    jiraUser.setName(segs[0]);

    return jiraUser;
  }


}
