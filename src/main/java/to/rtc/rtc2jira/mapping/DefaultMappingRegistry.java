package to.rtc.rtc2jira.mapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import to.rtc.rtc2jira.spi.Mapping;
import to.rtc.rtc2jira.spi.MappingRegistry;
import to.rtc.rtc2jira.storage.WorkItemConstants;

import com.ibm.team.workitem.common.model.IWorkItem;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class DefaultMappingRegistry implements MappingRegistry {
  private final static DefaultMappingRegistry INSTANCE = new DefaultMappingRegistry();

  private Mapping missingMapping = new MissingMapping();

  private DefaultMappingRegistry() {
    register(RTCIdentifierConstants.ID, new NullMapping());
    register(RTCIdentifierConstants.SUMMARY, new DirectStringMapping(WorkItemConstants.SUMMARY));
    register(RTCIdentifierConstants.DESCRIPTION, new DirectStringMapping(WorkItemConstants.DESCRIPTION));
    register(RTCIdentifierConstants.WORK_ITEM_TYPE, new DirectStringMapping(WorkItemConstants.WORK_ITEM_TYPE));
    register(RTCIdentifierConstants.ACCEPTANCE_CRITERIAS, new DirectStringMapping(
        WorkItemConstants.ACCEPTANCE_CRITERIAS));
    register(RTCIdentifierConstants.MODIFIED, new DirectDateMapping(WorkItemConstants.MODIFIED));
    register(RTCIdentifierConstants.CREATIONDATE, new DirectDateMapping(WorkItemConstants.CREATIONDATE));
    register(RTCIdentifierConstants.COMMENTS, new CommentMapping());
    register(RTCIdentifierConstants.PRIORITY, new PriorityMapping());
    register(RTCIdentifierConstants.SEVERITY, new SeverityMapping());
    register(RTCIdentifierConstants.OWNER, new ContributorMapping(WorkItemConstants.OWNER));
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
