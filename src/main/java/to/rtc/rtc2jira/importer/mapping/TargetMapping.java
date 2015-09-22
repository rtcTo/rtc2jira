/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.importer.mapping;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;

import to.rtc.rtc2jira.importer.mapping.spi.MappingAdapter;
import to.rtc.rtc2jira.storage.FieldNames;

import com.ibm.team.process.internal.common.Iteration;
import com.ibm.team.process.internal.common.IterationHandle;
import com.ibm.team.process.internal.common.IterationType;
import com.ibm.team.workitem.common.model.IAttribute;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * @author roman.schaller
 *
 */
public class TargetMapping extends MappingAdapter {
  static final Logger LOGGER = Logger.getLogger(TargetMapping.class.getName());

  public static final String NO_ITERATION = "NO_ITERATION";

  private IterationInfo iterationInfo;

  @Override
  protected void beforeWorkItem() {
    iterationInfo = null;
  }

  @Override
  public void acceptAttribute(IAttribute attribute) {
    IterationHandle handle = getValue(attribute);
    if (handle != null) {
      Iteration iteration = fetchCompleteItem(handle);
      iterationInfo = fromRtcIteration(iteration);
    }
  }

  IterationInfo fromRtcIteration(Iteration iteration) {
    IterationInfo iterationInfo = new IterationInfo();
    iterationInfo.rtcId = iteration.getId();
    iterationInfo.name = iteration.getName();
    iterationInfo.label = iteration.getLabel();
    iterationInfo.startDate = iteration.getStartDate();
    iterationInfo.endDate = iteration.getEndDate();
    iterationInfo.hasDeliverable = iteration.hasDeliverable();
    iterationInfo.archived = iteration.isArchived();
    if (iteration.getParent() != null) {
      iterationInfo.parent = fromRtcIteration(fetchCompleteItem(iteration.getParent()));
    }
    if (iteration.getIterationType() != null) {
      IterationType iterationType = fetchCompleteItem(iteration.getIterationType());
      iterationInfo.iterationType = iterationType.getLabel();
    } else {
      if (iterationInfo.name.toLowerCase().contains("release")) {
        iterationInfo.iterationType = "Release";
        iterationInfo.hasDeliverable = true;
      } else {
        iterationInfo.iterationType = "Sprint";
      }
    }
    return iterationInfo;
  }


  @Override
  public void afterWorkItem(ODocument doc) {
    if (iterationInfo != null) {
      String iterationJson = iterationInfo.marshall();
      doc.field(FieldNames.ITERATION_LABEL, iterationJson);
    } else {
      doc.field(FieldNames.ITERATION_LABEL, NO_ITERATION);
    }
  }


  @XmlRootElement
  @XmlAccessorType(XmlAccessType.FIELD)
  static public class IterationInfo {
    public String rtcId;
    public String name;
    public String label;
    public Date startDate;
    public Date endDate;
    public IterationInfo parent;
    public boolean hasDeliverable;
    public boolean archived;
    public String iterationType;
    @JsonIgnore
    private ObjectMapper objectMapper;

    public IterationInfo() {
      objectMapper = new ObjectMapper();
    }


    String marshall() {
      String result = "";
      try {
        result = objectMapper.writeValueAsString(this);
      } catch (IOException e) {
        LOGGER.log(Level.SEVERE, "Could not marshall IterationInfo: " + e.getMessage());
      }
      return result;
    }

    public void unmarshall(String json) throws IOException {
      try {
        IterationInfo readValue = objectMapper.readValue(json, IterationInfo.class);
        this.rtcId = readValue.rtcId;
        this.name = readValue.name;
        this.label = readValue.label;
        this.startDate = readValue.startDate;
        this.endDate = readValue.endDate;
        this.hasDeliverable = readValue.hasDeliverable;
        this.archived = readValue.archived;
        this.parent = readValue.parent;
        this.iterationType = readValue.iterationType;
      } catch (IOException e) {
        throw e;
      }

    }
  }

}
