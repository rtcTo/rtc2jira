package to.rtc.rtc2jira.mapping;

import com.ibm.team.workitem.common.model.IAttribute;

import to.rtc.rtc2jira.spi.MappingAdapter;

/**
 * This is actually not a real mapping. It just prints out the missing mappings.
 * 
 * @author roman
 *
 */
public class MissingMapping extends MappingAdapter {

	@Override
	public void acceptAttribute(IAttribute attribute) {
		String formattedOutput = String.format(
				"Unknown Attribute: Identifier: %s \t Display Name: %s \t Type: %s \t Value: %s",
				attribute.getIdentifier(), attribute.getDisplayName(), attribute.getAttributeType(),
				getValue(attribute));
		System.out.println(formattedOutput);
	}
}
