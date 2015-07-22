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
        "Unknown Attribute: Identifier: %-25s \t Display Name: %-20s \t Type: %-20s \t Value: %-40s",
        attribute.getIdentifier(), attribute.getDisplayName(), attribute.getAttributeType(),
        getValue(attribute));
    System.out.println(formattedOutput);
  }
}
