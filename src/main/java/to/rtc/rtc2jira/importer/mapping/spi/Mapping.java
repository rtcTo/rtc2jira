package to.rtc.rtc2jira.importer.mapping.spi;

import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.IWorkItem;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * This is the interface for all mappings being applied. With this you should be able to implement
 * all mappings from rtc to any target.
 * 
 * Implementations of {@link Mapping} are registered through your implementation of a
 * MappingFactoery. It is used in a thread save way. See the comments on methods to understand the
 * lifecycle.
 * 
 * @author roman
 *
 */
public interface Mapping {

  /**
   * This is called before each workitem.
   * 
   * @param workItem
   */
  public void beforeWorkItem(IWorkItem workItem);

  /**
   * This is called if a workitem contains an attribute for which this mapping is registered for.
   * 
   * @param attribute
   */
  public void acceptAttribute(IAttribute attribute);

  /**
   * This is called after all attributes have been accepted. Write your value into the
   * {@link ODocument}.
   * 
   * @param doc This is the intermediate store of the RTC workitems. From here we will send the
   *        workitems to jira or such.
   */
  public void afterWorkItem(ODocument doc);
}
