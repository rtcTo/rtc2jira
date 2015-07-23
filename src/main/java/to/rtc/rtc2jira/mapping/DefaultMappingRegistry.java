package to.rtc.rtc2jira.mapping;

import static to.rtc.rtc2jira.storage.WorkItemConstants.DESCRIPTION;
import static to.rtc.rtc2jira.storage.WorkItemConstants.SUMMARY;
import static to.rtc.rtc2jira.storage.WorkItemConstants.WORK_ITEM_TYPE;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import to.rtc.rtc2jira.spi.Mapping;
import to.rtc.rtc2jira.spi.MappingRegistry;

import com.ibm.team.workitem.common.model.IWorkItem;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class DefaultMappingRegistry implements MappingRegistry {
  private final static DefaultMappingRegistry INSTANCE = new DefaultMappingRegistry();

  private Mapping missingMapping = new MissingMapping();

  private DefaultMappingRegistry() {
    register(SUMMARY, new DirectMapping(SUMMARY));
    register(DESCRIPTION, new DirectMapping(DESCRIPTION));
    register(WORK_ITEM_TYPE, new DirectMapping(WORK_ITEM_TYPE));
  };

  public static DefaultMappingRegistry getInstance() {
    return INSTANCE;
  }

  private Map<String, Mapping> mappings = new HashMap<>();

  @Override
  public void register(String rtcIdentifier, Mapping mapping) {
    mappings.put(rtcIdentifier, mapping);
  }

  public Mapping getMapping(String rtcIdentifier) {
    return Optional.ofNullable(mappings.get(rtcIdentifier)).orElse(missingMapping);
  }

  public void beforeWorkItem(final IWorkItem workItem) {
    missingMapping.beforeWorkItem(workItem);
    mappings.values().forEach(m -> {
      m.beforeWorkItem(workItem);
    });
  }

  public void afterWorkItem(final ODocument doc) {
    missingMapping.afterWorkItem(doc);
    mappings.values().forEach(m -> {
      m.afterWorkItem(doc);
    });
  }

}
