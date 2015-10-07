package to.rtc.rtc2jira.importer.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.FieldNames;

import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.workitem.common.IWorkItemCommon;
import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.IAttributeHandle;
import com.ibm.team.workitem.common.model.IEnumeration;
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
    value = identifier.getStringIdentifier();
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (value != null && !value.isEmpty()) {
      doc.field(FieldNames.BISON_PROJECT_NAME, value);
    }
  }


  public Map<String, String> getAllCustomValues(IAttribute attribute) {
    IWorkItemCommon fWorkItemCommon = (IWorkItemCommon) getTeamRepository().getClientLibrary(IWorkItemCommon.class);

    // Iterate the enumeration literals and create
    IAttributeHandle attributeHandle = (IAttributeHandle) attribute.getItemHandle();
    IEnumeration<? extends ILiteral> targetEnumeration;
    Map<String, String> map = new HashMap<String, String>();
    try {
      targetEnumeration = fWorkItemCommon.resolveEnumeration(attributeHandle, null);
      List<? extends ILiteral> literals = targetEnumeration.getEnumerationLiterals();
      for (ILiteral targetLiteral : literals) {
        map.put(targetLiteral.getName(), targetLiteral.getIdentifier2().getStringIdentifier());
      }
    } catch (TeamRepositoryException e) {
      LOGGER.log(Level.SEVERE, "Problem while collecting value literals of enumeration", e);
    }
    return map;
  }
}
