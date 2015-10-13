/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.CustomFieldOption;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.TimeSlotEnum;

/**
 * @author gustaf.hansen
 *
 */
public class JiraTimeSlotMapping extends AbstractSelectionTypeMapping<TimeSlotEnum> {

  @Override
  protected void setFieldValue(Issue issue, TimeSlotEnum theEnum) {
    CustomFieldOption value = theEnum != null ? theEnum.getCustomFieldOption() : null;
    issue.getFields().setTimeSlot(value);
  }

  @Override
  protected TimeSlotEnum getDefaultValue() {
    return TimeSlotEnum.UNSET;
  }
}
