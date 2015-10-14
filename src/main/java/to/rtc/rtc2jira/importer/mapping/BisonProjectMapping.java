package to.rtc.rtc2jira.importer.mapping;

import java.util.logging.Logger;

import to.rtc.rtc2jira.importer.RTCImporter;
import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.FieldNames;

import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.ILiteral;
import com.ibm.team.workitem.common.model.Identifier;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class BisonProjectMapping extends MappingAdapter {
  static final Logger LOGGER = Logger.getLogger(BisonProjectMapping.class.getName());
  static {
    LOGGER.addHandler(RTCImporter.DEFAULT_LOG_HANDLER);
  }
  private String value;

  @Override
  protected void beforeWorkItem() {
    value = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    Identifier<ILiteral> identifier = getValue(attribute);
    value = identifier.getStringIdentifier();
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (value != null && !value.isEmpty()) {
      doc.field(FieldNames.BISON_PROJECT_NAME, value);
    }
  }

}
