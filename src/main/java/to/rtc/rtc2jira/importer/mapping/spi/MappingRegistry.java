package to.rtc.rtc2jira.importer.mapping.spi;

/**
 * Use this registry to tell the importer which RTC identifier you wish to handle on your own. Some
 * identifier are already registered. You can simply override it by reregistering it.
 * 
 * @author roman
 *
 */
public interface MappingRegistry {

  /**
   * Tells the importer to use the appropriate {@link Mapping} for a given RTC identifier.
   * 
   * @param rtcIdentifier identifies the attribute on a workitem
   * @param mapping implementation which handles an attribute
   */
  public void register(String rtcIdentifier, Mapping mapping);

}
