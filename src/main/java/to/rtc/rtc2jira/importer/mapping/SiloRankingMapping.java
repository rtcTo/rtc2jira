package to.rtc.rtc2jira.importer.mapping;

import java.util.Map;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.FieldNames;

import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.ILiteral;
import com.ibm.team.workitem.common.model.Identifier;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class SiloRankingMapping extends MappingAdapter {
  private Integer value;

  @Override
  protected void beforeWorkItem() {
    value = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    Map<String, String> allCustomValues = getAllCustomValues(attribute);
    Identifier<ILiteral> identifier = getValue(attribute);
    String stringIdentifier = identifier.getStringIdentifier();
    String label = allCustomValues.get(stringIdentifier);
    value = Integer.valueOf(label);
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (value != null) {
      doc.field(FieldNames.SILO_RANKING, value);
    }
  }


}
