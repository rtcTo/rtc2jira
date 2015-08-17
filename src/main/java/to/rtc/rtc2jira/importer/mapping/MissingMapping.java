package to.rtc.rtc2jira.importer.mapping;

import java.util.logging.Logger;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;

import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * This is actually not a real mapping. It just prints out the missing mappings. Mappings are
 * considered as missing if their value is not <code>null</code>.
 * 
 * @author roman
 *
 */
public class MissingMapping extends MappingAdapter {
  private static final Logger LOGGER = Logger.getLogger(MissingMapping.class.getName());

  @Override
  public void acceptAttribute(IAttribute attribute) {
    Object value = getValue(attribute);
    if (value != null) {
      String formattedOutput =
          String.format("Unknown Attribute: Identifier: "
              + "%-25s \t Display Name: %-20s \t Type: %-20s \t Value: %-40s", attribute.getIdentifier(),
              attribute.getDisplayName(), attribute.getAttributeType(), value);
      LOGGER.warning(formattedOutput);
    }
  }

  @Override
  public void afterWorkItem(ODocument doc) {}

  @Override
  protected void beforeWorkItem() {}
}
