/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.importer.mapping;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.FieldNames;

import com.ibm.team.process.common.ITeamAreaHandle;
import com.ibm.team.process.common.ITeamAreaHierarchy;
import com.ibm.team.process.internal.common.ProjectArea;
import com.ibm.team.process.internal.common.TeamArea;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.workitem.client.IWorkItemClient;
import com.ibm.team.workitem.common.internal.model.Category;
import com.ibm.team.workitem.common.model.CategoryId;
import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.ICategoryHandle;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * @author roman.schaller
 *
 */
public class CategoryMapping extends MappingAdapter {

  public static final String NO_TEAM = "NO_TEAM";
  public static final String NO_CATEGORY = "NO_CATEGORY";

  public static final String FIELD_SEPARATOR = "|#|";

  private String value;

  @Override
  protected void beforeWorkItem() {
    value = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    ICategoryHandle categoryHandle = getValue(attribute);
    Category category = fetchCompleteItem(categoryHandle);
    String categoryQualifiedName = getCategoryQualifiedName(category.getCategoryId());
    if (categoryQualifiedName == null || categoryQualifiedName.isEmpty()) {
      categoryQualifiedName = NO_CATEGORY;
    }
    ITeamAreaHandle defaultTeamArea = category.getDefaultTeamArea();
    String teamAreaQualifiedName = getTeamAreaQualifiedName(defaultTeamArea);
    if (teamAreaQualifiedName == null || teamAreaQualifiedName.isEmpty()) {
      teamAreaQualifiedName = NO_TEAM;
    }
    value = categoryQualifiedName + FIELD_SEPARATOR + teamAreaQualifiedName;
  }

  private String getCategoryQualifiedName(CategoryId categoryId) {
    String qualifiedName = categoryId.getSubtreePattern();
    String[] split = qualifiedName.split("/");
    List<String> nameSegs = new LinkedList<String>(Arrays.asList(split));
    if ("".equals(nameSegs.get(0))) {
      nameSegs.remove(0);
    }
    if ("Unassigned".equals(nameSegs.get(0))) {
      nameSegs.remove(0);
    }
    if ("%".equals(nameSegs.get(nameSegs.size() - 1))) {
      nameSegs.remove(nameSegs.size() - 1);
    }
    StringBuilder builder = new StringBuilder();
    for (String name : nameSegs) {
      if (builder.length() > 0) {
        builder.append("/");
      }
      builder.append(name);
    }
    return builder.toString();
  }

  @Override
  public void afterWorkItem(ODocument doc) {
    doc.field(FieldNames.CATEGORY, value);
  }


  private String getTeamAreaQualifiedName(ITeamAreaHandle teamAreaHandle) {
    StringBuilder result = new StringBuilder();
    ProjectArea projectArea = getProjectArea();
    ITeamAreaHierarchy teamAreaHierarchy = projectArea.getTeamAreaHierarchy();
    while (teamAreaHandle != null) {
      TeamArea teamArea = fetchCompleteItem(teamAreaHandle);
      if (result.length() > 0) {
        result.insert(0, '/');
      }
      result.insert(0, teamArea.getName());
      teamAreaHandle = teamAreaHierarchy.getParent(teamAreaHandle);
    }
    return result.toString();
  }

  protected ICategoryHandle resolveCategoryId(CategoryId id) throws TeamRepositoryException {
    ITeamRepository teamRepository = getTeamRepository();
    IWorkItemClient workItemClient = (IWorkItemClient) teamRepository.getClientLibrary(IWorkItemClient.class);

    String subtreePattern = id.getSubtreePattern();
    List<String> path = Arrays.asList(subtreePattern.split("/"));

    ICategoryHandle category = null;

    try {
      category = workItemClient.findCategoryByNamePath(getWorkItem().getProjectArea(), path, null);
    } catch (Exception e) {
      String message =
          "RTCClient: setWorkItemCategory() - findCategoryByNamePath() failed for categoryName '" + subtreePattern
              + "'!!" + e + ":" + e.getMessage();
      throw new TeamRepositoryException(message, e);
    }

    if (category == null) {
      throw new TeamRepositoryException(
          "RTCClient: modifyWorkItemWorkingCopyWithoutSave() - findCategoryByNamePath() failed for categoryName '"
              + subtreePattern + "'!!");
    }

    return category;
  }

}
