package to.rtc.rtc2jira.mapping;

import org.apache.commons.lang3.StringEscapeUtils;

import to.rtc.rtc2jira.spi.MappingAdapter;

import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class DirectMapping extends MappingAdapter {

  private String value;
  private String identifier;

  public DirectMapping(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public void beforeWorkItem() {
    value = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    String rawVal = getValue(attribute);
    if ("mediumHtml".equalsIgnoreCase(attribute.getAttributeType())) {
      value = StringEscapeUtils.unescapeXml(rawVal);
    } else {
      value = rawVal;
    }
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (value != null) {
      doc.field(identifier, value);
    }
  }

}
