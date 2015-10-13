/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author roman.schaller
 *
 */
public class DescriptionMapping implements Mapping {

  @Override
  public void map(Object value, Issue issue, StorageEngine storage) {
    String description = (String) value;
    // line breaks
    description = description.replaceAll("<br/>", "\r\n");
    // bold
    description = description.replaceAll("<b>", "*");
    description = description.replaceAll("</b>", "*");
    // italics
    description = description.replaceAll("<i>", "_");
    description = description.replaceAll("</i>", "_");
    // emphasis
    description = description.replaceAll("<em>", "_");
    description = description.replaceAll("</em>", "_");
    // deleted
    description = description.replaceAll("<del>", "-");
    description = description.replaceAll("</del>", "-");
    // inserted
    description = description.replaceAll("<ins>", "+");
    description = description.replaceAll("</ins>", "+");
    // superscript
    description = description.replaceAll("<sup>", "^");
    description = description.replaceAll("</sup>", "^");
    // subscript
    description = description.replaceAll("<sub>", "~");
    description = description.replaceAll("</sub>", "~");

    issue.getFields().setDescription(description);
  }
}
