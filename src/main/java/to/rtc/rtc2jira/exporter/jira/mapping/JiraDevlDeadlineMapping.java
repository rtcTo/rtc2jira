/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.CustomFieldOption;
import to.rtc.rtc2jira.exporter.jira.entities.DevlDeadlineEnum;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;

/**
 * @author gustaf.hansen
 *
 */
public class JiraDevlDeadlineMapping extends AbstractSelectionTypeMapping<DevlDeadlineEnum> {

  @Override
  protected void setFieldValue(Issue issue, DevlDeadlineEnum theEnum) {
    CustomFieldOption value = theEnum != null ? theEnum.getCustomFieldOption() : null;
    issue.getFields().setDevlDeadline(value);
  }

  @Override
  protected DevlDeadlineEnum getDefaultValue() {
    return DevlDeadlineEnum.UNSET;
  }
}
