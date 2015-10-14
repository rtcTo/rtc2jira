/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.CustomFieldOption;
import to.rtc.rtc2jira.exporter.jira.entities.ImplementationLevelEnum;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;

/**
 * @author gustaf.hansen
 *
 */
public class JiraImplementationLevelMapping extends AbstractSelectionTypeMapping<ImplementationLevelEnum> {

  @Override
  protected void setFieldValue(Issue issue, ImplementationLevelEnum theEnum) {
    CustomFieldOption value = theEnum != null ? theEnum.getCustomFieldOption() : null;
    issue.getFields().setImplementationLevel(value);
  }

  @Override
  protected ImplementationLevelEnum getDefaultValue() {
    return ImplementationLevelEnum.Standard_Basis_Grosshandel;
  }
}
