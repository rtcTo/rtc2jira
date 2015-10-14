/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.CustomFieldOption;
import to.rtc.rtc2jira.exporter.jira.entities.CustomerEnum;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;

/**
 * @author gustaf.hansen
 *
 */
public class JiraCustomerMapping extends AbstractSelectionTypeMapping<CustomerEnum> {

  @Override
  protected void setFieldValue(Issue issue, CustomerEnum theEnum) {
    CustomFieldOption value = theEnum != null ? theEnum.getCustomFieldOption() : null;
    issue.getFields().setCustomer(value);
  }

  @Override
  protected CustomerEnum getDefaultValue() {
    return CustomerEnum.UNSET;
  }
}
