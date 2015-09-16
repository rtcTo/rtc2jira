package to.rtc.rtc2jira.importer.mapping.spi;

import com.ibm.team.process.internal.common.ProjectArea;
import com.ibm.team.repository.client.IItemManager;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.client.internal.ItemManager;
import com.ibm.team.repository.common.IItemHandle;
import com.ibm.team.repository.common.TeamRepositoryException;
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
public abstract class MappingAdapter implements Mapping {

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
  protected abstract void beforeWorkItem();

  protected IWorkItem getWorkItem() {
    return workItem;
  }

  protected ITeamRepository getTeamRepository() {
    return ITeamRepository.class.cast(getWorkItem().getOrigin());
  }

  protected ProjectArea getProjectArea() {
    return fetchCompleteItem(getWorkItem().getProjectArea());
  }

  @SuppressWarnings("unchecked")
  protected <T> T getValue(IAttribute attribute) {
    return (T) workItem.getValue(attribute);
  }

  @SuppressWarnings("unchecked")
  protected <T> T fetchCompleteItem(IItemHandle itemHandle) {
    T completeItem = null;
    try {
      IItemManager itemManager = getTeamRepository().itemManager();
      completeItem = (T) itemManager.fetchCompleteItem(itemHandle, ItemManager.DEFAULT, null);
    } catch (TeamRepositoryException e) {
      e.printStackTrace();
    }
    return completeItem;
  }


}
