/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.jira.mapping;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import to.rtc.rtc2jira.exporter.jira.entities.Issue;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;

import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * @author roman.schaller
 *
 */
public class MappingRegistry {

  private final Map<String, Mapping> registry = new LinkedHashMap<>();

  public MappingRegistry() {
    registry.put(FieldNames.SUMMARY, new SummaryMapping());
    registry.put(FieldNames.DUE_DATE, new DueDateMapping());
    registry.put(FieldNames.DESCRIPTION, new DescriptionMapping());
    registry.put(FieldNames.PTF, new PtfMapping());
    registry.put(FieldNames.WORK_ITEM_TYPE, new WorkItemTypeMapping());
    registry.put(FieldNames.STATE, new StatusMapping());
    registry.put(FieldNames.RESOLUTION, new ResolutionMapping());
    registry.put(FieldNames.RESOLUTION_DATE, new ResolutiondateMapping());
    registry.put(FieldNames.CREATOR, new ReporterMapping());
    registry.put(FieldNames.OWNER, new OwnerMapping());
    registry.put(FieldNames.RESOLVER, new ResolverMapping());
    registry.put(FieldNames.TAGS, new LabelsMapping());
    registry.put(FieldNames.CREATIONDATE, new CreationDateMapping());
    registry.put(FieldNames.STORY_POINTS, new StoryPointsMapping());
    registry.put(FieldNames.ACCEPTANCE_CRITERIAS, new AcceptanceCriteriaMapping());
    registry.put(FieldNames.SEVERITY, new SeverityMapping());
    registry.put(FieldNames.PRIORITY, new PriorityMapping());
    registry.put(FieldNames.ARCHIVED, new ArchivedMapping());
    registry.put(FieldNames.CATEGORY, new JiraCategoryMapping());
    registry.put(FieldNames.SUBSCRIPTIONS, new WatcherMapping());
    registry.put(FieldNames.ITERATION_INFO, new IterationMapping());
    registry.put(FieldNames.ESTIMATED_TIME, new EstimatedHoursMapping());
    registry.put(FieldNames.BISON_PROJECT_NAME, new BisonProjectNameMapping());
    registry.put(FieldNames.REF_MODEL, new RefModelMapping());
    registry.put(FieldNames.EP_REQ_NR, new EpReqNrMapping());
    registry.put(FieldNames.EP_REQ_NR_UG, new EpReqNrUgMapping());
    registry.put(FieldNames.DP_REQ_NR, new DpReqNrMapping());
    registry.put(FieldNames.MARKET_BUDGET, new MarketBudgetMapping());
    registry.put(FieldNames.COMPETENCE_CENTER, new CompetenceCenterMapping());
    registry.put(FieldNames.PO_PREPLANNING_DONE, new PoPreplanningDoneMapping());
    registry.put(FieldNames.RELATED_PROJECTS, new RelatedProjectsMapping());
    registry.put(FieldNames.INDUSTRY_SECTOR, new IndustrySectorMapping());
    registry.put(FieldNames.DEVL_DEADLINE, new JiraDevlDeadlineMapping());
    registry.put(FieldNames.SECOND_ESTIMATION, new SecondEstimationMapping());
    registry.put(FieldNames.TEST_SCRIPT_IDs, new TestScriptIdsMapping());
    registry.put(FieldNames.TIME_SLOT, new JiraTimeSlotMapping());
    registry.put(FieldNames.SILO_RANKING, new JiraSiloRankingMapping());
    registry.put(FieldNames.PRODUCT_OWNER, new ProductOwnerMapping());
  }

  public void map(ODocument workItem, Issue issue, StorageEngine storage) {
    Set<Entry<String, Mapping>> mappers = registry.entrySet();
    for (Entry<String, Mapping> mapper : mappers) {
      Object field = workItem.field(mapper.getKey());
      mapper.getValue().map(field, issue, storage);
    }
  }

}
