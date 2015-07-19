/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.extract;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.IWorkItem;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * @author roman.schaller
 *
 */
public class AttributeMapper {

  private static final Set<String> DIRECT_STANDARD_ATTRIBUTES;

  static {
    DIRECT_STANDARD_ATTRIBUTES = new HashSet<>();
    DIRECT_STANDARD_ATTRIBUTES.add("summary");
    DIRECT_STANDARD_ATTRIBUTES.add("description");
  }

  public void map(List<IAttribute> allAttributes, ODocument doc, IWorkItem workItem) {
    for (IAttribute attribute : allAttributes) {
      if (workItem.hasAttribute(attribute)) {
        Object value = workItem.getValue(attribute);
        String identifier = attribute.getIdentifier();
        String displayName = attribute.getDisplayName();
        String attributeType = attribute.getAttributeType();
        if (DIRECT_STANDARD_ATTRIBUTES.contains(identifier)) {
          doc.field(identifier, value);
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
