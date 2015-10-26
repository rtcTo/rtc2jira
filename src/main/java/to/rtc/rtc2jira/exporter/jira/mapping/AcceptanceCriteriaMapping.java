/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import org.apache.commons.lang3.StringEscapeUtils;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class AcceptanceCriteriaMapping implements Mapping {

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    String acceptanceCriteria = (String) value;
    if (acceptanceCriteria != null) {
      // html tags (open-close)
      acceptanceCriteria = acceptanceCriteria.replaceAll("(?i)<td[^>]*>", " ").replaceAll("\\s+", " ").trim();
      // line breaks
      acceptanceCriteria = acceptanceCriteria.replaceAll("<br/>", "\r\n");
      // entities
      acceptanceCriteria = StringEscapeUtils.unescapeHtml4(acceptanceCriteria);
      issue.getFields().setAcceptanceCriteria(acceptanceCriteria);
    }
  }
}
