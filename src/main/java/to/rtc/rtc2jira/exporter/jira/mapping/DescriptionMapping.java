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
    description = convertHtmlToJiraMarkup(description);
    if (description != null) {
      issue.getFields().setDescription(description);
    }
  }


  public static String convertHtmlToJiraMarkup(String text) {
    if (text != null) {
      // line breaks
      text = text.replaceAll("<br/>", "\r\n");
      // bold
      text = text.replaceAll("<b>", "*");
      text = text.replaceAll("</b>", "*");
      // italics
      text = text.replaceAll("<i>", "_");
      text = text.replaceAll("</i>", "_");
      // emphasis
      text = text.replaceAll("<em>", "_");
      text = text.replaceAll("</em>", "_");
      // deleted
      text = text.replaceAll("<del>", "-");
      text = text.replaceAll("</del>", "-");
      // inserted
      text = text.replaceAll("<ins>", "+");
      text = text.replaceAll("</ins>", "+");
      // superscript
      text = text.replaceAll("<sup>", "^");
      text = text.replaceAll("</sup>", "^");
      // subscript
      text = text.replaceAll("<sub>", "~");
      text = text.replaceAll("</sub>", "~");
    }
    return text;
  }

}
