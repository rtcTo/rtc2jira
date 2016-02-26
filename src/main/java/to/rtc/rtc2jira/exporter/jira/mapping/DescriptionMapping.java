/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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

    // handle anchors

    int found = text.indexOf("<a ", 0);

    while (found > -1) {
      int anchorEnd = text.indexOf("</a>");
      anchorEnd += 4;
      Node containerNode = parseXml(text.substring(found));
      if (containerNode != null) {
        Node anchor = containerNode.getFirstChild();
        String textContent = anchor.getTextContent();
        String link = anchor.getAttributes().getNamedItem("href").getNodeValue();
        if (textContent != null) {
          textContent = textContent.trim();
          if (!textContent.isEmpty()) {
            textContent += "|";
          }
        } else {
          textContent = "";
        }
        String jiraAnchor = "[" + textContent + link + "]";
        // insert Jira anchor
        text = text.substring(0, found) + jiraAnchor + (anchorEnd < text.length() ? text.substring(anchorEnd) : "");
      }
      found++;
      found = text.indexOf("<a ", found);
    }
    return text;
  }


  public static Node parseXml(String fragment) {
    // Wrap the fragment in an arbitrary element.
    fragment = "<fragment>" + fragment + "</fragment>";
    try {
      // Create a DOM builder and parse the fragment.
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      Document d = factory.newDocumentBuilder().parse(new InputSource(new StringReader(fragment)));

      // Return the fragment.
      return d.getFirstChild();
    } catch (SAXException e) {
      // A parsing error occurred; the XML input is not valid.
    } catch (ParserConfigurationException e) {
    } catch (IOException e) {
    }
    return null;
  }

}
