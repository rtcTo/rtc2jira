package to.rtc.rtc2jira.spi;

import com.ibm.team.repository.client.IItemManager;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.client.internal.ItemManager;
import com.ibm.team.repository.common.IContributorHandle;
import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.repository.common.model.Contributor;
import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.IWorkItem;

/**
 * An easy implementation to help implementors of {@link Mapping}.
 * 
 * It adds a getter for the {@link IWorkItem} and a convenience methods
 * {@link #getValue(IAttribute)}.
 * 
 * @author roman
 *
 */
public class MappingAdapter implements Mapping {

  private IWorkItem workItem;

  @Override
  public final void beforeWorkItem(IWorkItem workItem) {
    this.workItem = workItem;
    beforeWorkItem();
  }

  /**
   * Override this method for example to cleanup your member variables before the next work item.
   * {@link #getWorkItem()} already returns the next {@link IWorkItem}.
   */
  protected void beforeWorkItem() {}

  @Override
  public void acceptAttribute(IAttribute attribute) {}

  protected IWorkItem getWorkItem() {
    return workItem;
  }

  protected ITeamRepository getTeamRepository() {
    return ITeamRepository.class.cast(getWorkItem().getOrigin());
  }

  @SuppressWarnings("unchecked")
  protected <T> T getValue(IAttribute attribute) {
    return (T) workItem.getValue(attribute);
  }

  protected Contributor loadContributor(IContributorHandle contributorHandle) {
    Contributor rtcCreator = null;
    try {
      IItemManager itemManager = ITeamRepository.class.cast(getWorkItem().getOrigin()).itemManager();
      rtcCreator = (Contributor) itemManager.fetchCompleteItem(contributorHandle, ItemManager.DEFAULT, null);
    } catch (TeamRepositoryException e) {
      e.printStackTrace();
    }
    return rtcCreator;
  }
}
