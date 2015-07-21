/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.extract;

import java.util.List;

import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.IWorkItem;
import com.orientechnologies.orient.core.record.impl.ODocument;

import to.rtc.rtc2jira.mapping.DefaultMappingRegistry;
import to.rtc.rtc2jira.spi.Mapping;

/**
 * @author roman.schaller
 *
 */
public class AttributeMapper {

	public void map(List<IAttribute> allAttributes, ODocument doc, IWorkItem workItem) {
		DefaultMappingRegistry.getInstance().beforeWorkItem(workItem);
		for (IAttribute attribute : allAttributes) {
			if (workItem.hasAttribute(attribute)) {
				String identifier = attribute.getIdentifier();
				Mapping mapping = DefaultMappingRegistry.getInstance().getMapping(identifier);
				mapping.acceptAttribute(attribute);
			}
		}
		DefaultMappingRegistry.getInstance().afterWorkItem(doc);
	}
}
