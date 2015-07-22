package to.rtc.rtc2jira.spi;

import java.util.ServiceLoader;

/**
 * Implement this factory and make it available via {@link ServiceLoader} to register your ouwn
 * mappings.
 * 
 * @author roman
 *
 */
public interface MappingFactoery {

  /**
   * Called once at startup. Register your mappers here.
   * 
   * @param registry
   */
  public void registerMappings(MappingRegistry registry);
}
