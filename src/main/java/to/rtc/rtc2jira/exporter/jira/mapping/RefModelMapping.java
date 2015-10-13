/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.CustomFieldOption;
import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.exporter.jira.entities.RefModelEnum;

/**
 * @author gustaf.hansen
 *
 */
public class RefModelMapping extends AbstractSelectionTypeMapping<RefModelEnum> {


  @Override
  protected void setFieldValue(Issue issue, RefModelEnum theEnum) {
    CustomFieldOption customFieldOption = theEnum != null ? theEnum.getCustomFieldOption() : null;
    issue.getFields().setRefModel(customFieldOption);
  }


  @Override
  protected RefModelEnum getDefaultValue() {
    return RefModelEnum.Standard;
  }


}
