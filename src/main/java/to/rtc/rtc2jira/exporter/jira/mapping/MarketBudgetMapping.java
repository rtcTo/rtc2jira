/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author gustaf.hansen
 *
 */
public class MarketBudgetMapping implements Mapping {

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    if (value != null) {
      Integer budget = (Integer) value;
      if (budget > 0) {
        issue.getFields().setMarketBudget(budget);
      } else {
        issue.getFields().setMarketBudget(null);
      }
    } else {
      issue.getFields().setMarketBudget(null);
    }
  }
}
