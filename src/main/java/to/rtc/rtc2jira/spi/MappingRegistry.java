package to.rtc.rtc2jira.spi;

/**
 * Use this registry to tell the extractor which RTC identifier you wish to handle on your own. Some
 * identifier are already registered. You can simply override it by reregistering it.
 * 
 * @author roman
 *
 */
public interface MappingRegistry {

  /**
   * Tells the extractor to use the appropriate {@link Mapping} for a given RTC identifier.
   * 
   * @param rtcIdentifier
   * @param mapping
   */
  public void register(String rtcIdentifier, Mapping mapping);

}
