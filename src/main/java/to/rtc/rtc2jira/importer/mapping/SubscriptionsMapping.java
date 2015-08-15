package to.rtc.rtc2jira.importer.mapping;

import java.util.LinkedList;
import java.util.List;

import com.ibm.team.repository.common.IContributor;
import com.ibm.team.repository.common.IContributorHandle;
import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.FieldNames;

public class SubscriptionsMapping extends MappingAdapter {

  private List<String> subscriptions;

  @Override
  protected void beforeWorkItem() {
    subscriptions = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    List<IContributorHandle> rtcSubscriptions = getValue(attribute);
    subscriptions = new LinkedList<>();
    for (IContributorHandle handle : rtcSubscriptions) {
      IContributor subscriber = fetchCompleteItem(handle);
      String s = String.format("%s <%s>", subscriber.getName(), subscriber.getEmailAddress());
      subscriptions.add(s);
    }
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    if (subscriptions != null && !subscriptions.isEmpty()) {
      doc.field(FieldNames.SUBSCRIPTIONS, subscriptions);
    }
  }
}
