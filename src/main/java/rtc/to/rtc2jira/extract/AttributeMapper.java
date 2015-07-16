/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package rtc.to.rtc2jira.extract;

import java.util.List;

import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.IWorkItem;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * @author roman.schaller
 *
 */
public class AttributeMapper {



  public void map(List<IAttribute> allAttributes, ODocument doc, IWorkItem workItem) {
    for (IAttribute attribute : allAttributes) {
      if (workItem.hasAttribute(attribute)) {
        Object value = workItem.getValue(attribute);
        String identifier = attribute.getIdentifier();
        String displayName = attribute.getDisplayName();
        String attributeType = attribute.getAttributeType();
        if (identifier.equals("summary") && attributeType.equals("mediumHtml")) {
          doc.field("summery", value);
        } else {
          String formattedOutput =
              String.format("Unknown Attribute: Identifier: %s \t Display Name: %s \t Type: %s \t Value: %s",
                  identifier, displayName, attributeType, value);
          System.out.println(formattedOutput);
        }
      }
    }
  }
}
