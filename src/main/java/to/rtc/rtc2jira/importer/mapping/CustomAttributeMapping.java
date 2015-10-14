/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.importer.mapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import to.rtc.rtc2jira.importer.RTCImporter;
import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;

import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.IAttributeHandle;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * @author roman.schaller
 *
 */
public class CustomAttributeMapping extends MappingAdapter {
  private static final Logger LOGGER = Logger.getLogger(CustomAttributeMapping.class.getName());
  static {
    LOGGER.addHandler(RTCImporter.DEFAULT_LOG_HANDLER);
  }

  private Set<String> customAttributes = new HashSet<>();

  @Override
  protected void beforeWorkItem() {}

  @Override
  public void acceptAttribute(IAttribute attribute) {
    List<IAttributeHandle> attributeHandles = getValue(attribute);
    for (IAttributeHandle attributeHandle : attributeHandles) {
      IAttribute a = fetchCompleteItem(attributeHandle);
      if (!customAttributes.contains(a.getIdentifier())) {
        customAttributes.add(a.getIdentifier());
        LOGGER.warning(String.format("Detected custom attribute %s / %s", a.getDisplayName(), a.getIdentifier()));
      }
    }
  }

  @Override
  public void afterWorkItem(ODocument doc) {}
}
