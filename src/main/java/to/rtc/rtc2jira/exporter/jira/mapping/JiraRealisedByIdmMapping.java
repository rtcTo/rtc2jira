/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.CustomFieldOption;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.RealisedByIdmEnum;

/**
 * @author gustaf.hansen
 *
 */
public class JiraRealisedByIdmMapping extends AbstractSelectionTypeMapping<RealisedByIdmEnum> {

  @Override
  protected void setFieldValue(Issue issue, RealisedByIdmEnum theEnum) {
    CustomFieldOption value = theEnum != null ? theEnum.getCustomFieldOption() : null;
    issue.getFields().setRealisedByIDM(value);
  }

  @Override
  protected RealisedByIdmEnum getDefaultValue() {
    return RealisedByIdmEnum.UNSET;
  }
}
