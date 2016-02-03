package to.rtc.rtc2jira.importer.mapping;

import java.util.logging.Logger;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.FieldNames;

import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.ILiteral;
import com.ibm.team.workitem.common.model.Identifier;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class BisonProjectMapping extends MappingAdapter {

  static final Logger LOGGER = Logger.getLogger(BisonProjectMapping.class.getName());
  private String value;

  @Override
  protected void beforeWorkItem() {
    value = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    Identifier<ILiteral> identifier = getValue(attribute);
    String strValue = identifier.getStringIdentifier();
    String defaultProjectName = getDefaultProjectName(attribute);
    if ("projektname.literal.l12".equals(defaultProjectName)) {
      value = mapItemValue(strValue);
    } else {
      value = strValue;
    }
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (value != null && !value.isEmpty()) {
      doc.field(FieldNames.BISON_PROJECT_NAME, value);
    }
  }

  private String getDefaultProjectName(IAttribute attribute) {
    String result = "";
    try {
      @SuppressWarnings("unchecked")
      Identifier<ILiteral> identifier =
          (Identifier<ILiteral>) attribute.getDefaultValue(
              (com.ibm.team.workitem.common.IAuditableCommon) getTeamRepository().getClientLibrary(
                  com.ibm.team.workitem.common.IAuditableCommon.class), this.getWorkItem(),
              new org.eclipse.core.runtime.NullProgressMonitor());
      if (identifier != null) {
        result = identifier.getStringIdentifier();
      }
    } catch (TeamRepositoryException e) {
    }
    return result;
  }

  String mapItemValue(String original) {
    return original + ".mapped";
  }

}
