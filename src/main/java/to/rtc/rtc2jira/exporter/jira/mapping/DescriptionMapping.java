/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringEscapeUtils;
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
    String rnProxy = "caburacelhota";
    String rn = "\r\n";

    if (text != null) {
      // line breaks
      text = text.replaceAll("<br/>", rnProxy);
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

      text = replaceHtmlAnchors(text, true);

      // delete remaining html tags (open-close)
      text = text.replaceAll("\\<.*?>", " ").replaceAll("\\s+", " ").trim();

      // entities
      text = StringEscapeUtils.unescapeHtml4(text);

      text = text.replaceAll(rnProxy, rn);
    }

    return text;
  }


  public static String replaceHtmlAnchors(String text, boolean toJiraMarkup) {
    int found = text.indexOf("<a ", 0);

    while (found > -1) {
      int anchorEnd = text.indexOf("</a>", found);
      anchorEnd += 4;
      Node containerNode = parseXml(text.substring(found, anchorEnd));
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
        String jiraAnchor = toJiraMarkup ? "[" + textContent + link + "]" : link;
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
