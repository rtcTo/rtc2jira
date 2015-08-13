package to.rtc.rtc2jira.importer.mapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import to.rtc.rtc2jira.importer.mapping.spi.Mapping;
import to.rtc.rtc2jira.importer.mapping.spi.MappingRegistry;
import to.rtc.rtc2jira.storage.WorkItemConstants;

import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.IWorkItem;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class DefaultMappingRegistry implements MappingRegistry {
  private final static DefaultMappingRegistry INSTANCE = new DefaultMappingRegistry();

  private Map<String, Mapping> mappings = new HashMap<>();
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
    register(RTCIdentifierConstants.CREATOR, new ContributorMapping(WorkItemConstants.CREATOR));
    register(RTCIdentifierConstants.MODIFIED_BY, new ContributorMapping(WorkItemConstants.MODIFIED_BY));
    register(RTCIdentifierConstants.RESOLVER, new ContributorMapping(WorkItemConstants.RESOLVER));
    register(RTCIdentifierConstants.DURATION, new NullMapping());
    register(RTCIdentifierConstants.CORRECTED_ESTIMATE, new NullMapping());
    register(RTCIdentifierConstants.TIME_SPENT, new NullMapping());
    register(RTCIdentifierConstants.CATEGORY, new CategoryMapping());
    register(RTCIdentifierConstants.ARCHIVED, new BooleanMapping(WorkItemConstants.ARCHIVED));
    register(RTCIdentifierConstants.CONTEXT_ID, new NullMapping());
    register(RTCIdentifierConstants.PROJECT_AREA, new ProjectAreaMapping());
    register(RTCIdentifierConstants.SEQUENCE_VALUE, new NullMapping());
    register(RTCIdentifierConstants.TAGS, new TagsMapping());
    register(RTCIdentifierConstants.STORY_POINTS, new StoryPointsMapping());
    register(RTCIdentifierConstants.CUSTOM_ATTRIBUTES, new CustomAttributeMapping());
    register(RTCIdentifierConstants.APPROVALS, new ApprovalMapping());
    register(RTCIdentifierConstants.APPROVAL_DESCRIPTORS, new ApprovalDescriptorMapping());
    register(RTCIdentifierConstants.RESOLUTION, new ResolutionMapping());
    register(RTCIdentifierConstants.STATE, new StateMapping());
    register(RTCIdentifierConstants.TARGET, new TargetMapping());
  };

  public static DefaultMappingRegistry getInstance() {
    return INSTANCE;
  }

  @Override
  public void register(String rtcIdentifier, Mapping mapping) {
    mappings.put(rtcIdentifier, mapping);
  }

  private Mapping getMapping(String rtcIdentifier) {
    return Optional.ofNullable(mappings.get(rtcIdentifier)).orElse(missingMapping);
  }

  public void beforeWorkItem(final IWorkItem workItem) {
    missingMapping.beforeWorkItem(workItem);
    mappings.values().forEach(m -> {
      m.beforeWorkItem(workItem);
    });
  }

  public void acceptAttribute(IAttribute attribute) {
    String identifier = attribute.getIdentifier();
    getMapping(identifier).acceptAttribute(attribute);
  }

  public void afterWorkItem(final ODocument doc) {
    missingMapping.afterWorkItem(doc);
    mappings.values().forEach(m -> {
      m.afterWorkItem(doc);
    });
  }

}
