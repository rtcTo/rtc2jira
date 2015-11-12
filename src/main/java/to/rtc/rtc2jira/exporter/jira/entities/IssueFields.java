package to.rtc.rtc2jira.exporter.jira.entities;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonView;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = NON_NULL)
public class IssueFields {

  private IssueType issuetype;
  private Project project;
  private String summary;
  private String description;
  private String ptf;
  private IssuePriority priority;
  private Date duedate;
  private IssueCommentContainer comment = new IssueCommentContainer();
  private Date updated;
  private IssueResolution resolution;
  private Date resolutiondate;
  private Date rtcResolutiondate;
  private JiraUser creator;
  private JiraUser reporter;
  private JiraUser assignee;
  private JiraUser resolver;
  private Date created;
  private List<IssueAttachment> attachment;
  private IssueStatus status;

  private String acceptanceCriteria;
  private List<String> labels = new ArrayList<String>();
  private Integer storyPoints;
  private Date rtcCreated;
  private JiraRadioItem archived;
  private Group team;
  private Set<IssueLink> issuelinks = new HashSet<IssueLink>();
  private Timetracking timetracking;
  private CustomFieldOption bisonProjectName;
  private CustomFieldOption refModel;
  private Integer epReqNr;
  private Integer epReqNrUg;
  private Integer dpReqNr;
  private Integer marketBudget;
  private CustomFieldOption competenceCenter;
  private JiraRadioItem poPreplanningDone;
  private List<String> relatedProjects = new ArrayList<String>();
  private List<String> industrySector = new ArrayList<String>();
  private CustomFieldOption devlDeadline;
  private Integer secondEstimation;
  private String testScriptIds;
  private CustomFieldOption timeSlot;
  private Integer siloRanking;
  private JiraUser productOwner;
  private JiraRadioItem roadmap;

  private CustomFieldOption customer;
  private CustomFieldOption realisedByIDM;
  private CustomFieldOption implementationLevel;

  private String filedAgainst;
  private String plannedFor;
  private String epicLink;
  private Issue parent;
  private String epicName;
  private List<Version> fixVersions = new ArrayList<Version>();


  public IssueType getIssuetype() {
    return issuetype;
  }

  public void setIssuetype(IssueType issuetype) {
    this.issuetype = issuetype;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    if (summary != null && summary.length() > 254) {
      summary = summary.substring(0, 250) + "...";
    }
    this.summary = summary;
  }

  @JsonView(IssueView.Update.class)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public IssuePriority getPriority() {
    return priority;
  }

  public void setPriority(IssuePriority priorty) {
    this.priority = priorty;
  }

  @XmlJavaTypeAdapter(JiraDateStringAdapter.class)
  @JsonView(IssueView.Update.class)
  public Date getDuedate() {
    return duedate;
  }

  public void setDuedate(Date duedate) {
    this.duedate = duedate;
  }

  @JsonView(IssueView.Read.class)
  public IssueCommentContainer getComment() {
    return comment;
  }

  public void setComment(IssueCommentContainer comment) {
    this.comment = comment;
  }

  @JsonView(IssueView.Read.class)
  public List<IssueAttachment> getAttachment() {
    return attachment;
  }

  public void setAttachment(List<IssueAttachment> attachment) {
    this.attachment = attachment;
  }

  @JsonView(IssueView.Read.class)
  public JiraUser getCreator() {
    return creator;
  }

  public void setCreator(JiraUser creator) {
    this.creator = creator;
  }

  @JsonView(IssueView.Read.class)
  @XmlJavaTypeAdapter(JiraDateStringAdapter.class)
  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  @JsonView(IssueView.Read.class)
  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  @JsonView(IssueView.Read.class)
  @XmlJavaTypeAdapter(JiraDateStringAdapter.class)
  public Date getResolutiondate() {
    return resolutiondate;
  }

  public void setResolutiondate(Date resolutiondate) {
    this.resolutiondate = resolutiondate;
  }

  @XmlElement(name = "customfield_10205")
  @XmlJavaTypeAdapter(JiraDateStringAdapter.class)
  public Date getRtcResolutiondate() {
    return rtcResolutiondate;
  }

  @JsonView(IssueView.Update.class)
  public void setRtcResolutiondate(Date rtcResolutiondate) {
    this.rtcResolutiondate = rtcResolutiondate;
  }

  public IssueResolution getResolution() {
    return resolution;
  }

  @JsonView(IssueView.Update.class)
  public void setResolution(IssueResolution resolution) {
    this.resolution = resolution;
  }

  @JsonView(IssueView.Update.class)
  public JiraUser getReporter() {
    return reporter;
  }

  public void setReporter(JiraUser reporter) {
    this.reporter = reporter;
  }

  @JsonView(IssueView.Update.class)
  public List<String> getLabels() {
    return labels;
  }

  public void setLabels(List<String> labels) {
    this.labels = labels;
  }

  @JsonView(IssueView.Read.class)
  public IssueStatus getStatus() {
    return status;
  }

  public void setStatus(IssueStatus status) {
    this.status = status;
  }

  @JsonView(IssueView.Update.class)
  public JiraUser getAssignee() {
    return assignee;
  }

  public void setAssignee(JiraUser assignee) {
    this.assignee = assignee;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10005")
  public Integer getStoryPoints() {
    return storyPoints;
  }

  public void setStoryPoints(Integer storyPoints) {
    this.storyPoints = storyPoints;
  }

  @JsonView(IssueView.Update.class)
  @XmlJavaTypeAdapter(JiraDateStringAdapter.class)
  @XmlElement(name = "customfield_10100")
  public Date getRtcCreated() {
    return rtcCreated;
  }

  public void setRtcCreated(Date rtcCreated) {
    this.rtcCreated = rtcCreated;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10101")
  public String getAcceptanceCriteria() {
    return acceptanceCriteria;
  }

  public void setAcceptanceCriteria(String acceptanceCriteria) {
    this.acceptanceCriteria = acceptanceCriteria;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10104")
  public JiraUser getResolver() {
    return resolver;
  }

  public void setResolver(JiraUser resolver) {
    this.resolver = resolver;
  }

  @XmlElement(name = "customfield_10204")
  @JsonView(IssueView.Update.class)
  public JiraRadioItem isArchived() {
    return archived;
  }

  public void setArchived(JiraRadioItem archived) {
    this.archived = archived;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10203")
  public Group getTeam() {
    return team;
  }

  public void setTeam(Group team) {
    this.team = team;
  }

  @JsonView(IssueView.Read.class)
  public Set<IssueLink> getIssuelinks() {
    return issuelinks;
  }

  public void setIssuelinks(Set<IssueLink> issuelinks) {
    this.issuelinks = issuelinks;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10206")
  public String getPtf() {
    return ptf;
  }

  public void setPtf(String ptf) {
    this.ptf = ptf;
  }

  @JsonView(IssueView.Update.class)
  public Timetracking getTimetracking() {
    return timetracking;
  }

  public void setTimetracking(Timetracking timetracking) {
    this.timetracking = timetracking;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10300")
  public CustomFieldOption getBisonProjectName() {
    return bisonProjectName;
  }

  public void setBisonProjectName(CustomFieldOption bisonProjectName) {
    this.bisonProjectName = bisonProjectName;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10400")
  public CustomFieldOption getRefModel() {
    return refModel;
  }

  public void setRefModel(CustomFieldOption refModel) {
    this.refModel = refModel;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10401", nillable = true)
  public Integer getEpReqNr() {
    return epReqNr;
  }

  public void setEpReqNr(Integer epReqNr) {
    this.epReqNr = epReqNr;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10402", nillable = true)
  public Integer getEpReqNrUg() {
    return epReqNrUg;
  }

  public void setEpReqNrUg(Integer epReqNrUg) {
    this.epReqNrUg = epReqNrUg;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10403", nillable = true)
  public Integer getDpReqNr() {
    return dpReqNr;
  }

  public void setDpReqNr(Integer dpReqNr) {
    this.dpReqNr = dpReqNr;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10404", nillable = true)
  public Integer getMarketBudget() {
    return marketBudget;
  }

  public void setMarketBudget(Integer marketBudget) {
    this.marketBudget = marketBudget;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10405")
  public CustomFieldOption getCompetenceCenter() {
    return competenceCenter;
  }

  public void setCompetenceCenter(CustomFieldOption competenceCenter) {
    this.competenceCenter = competenceCenter;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10406")
  public JiraRadioItem isPoPreplanningDone() {
    return poPreplanningDone;
  }

  public void setPoPreplanningDone(JiraRadioItem poPreplanningDone) {
    this.poPreplanningDone = poPreplanningDone;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10407")
  public List<String> getRelatedProjects() {
    return relatedProjects;
  }

  public void setRelatedProjects(List<String> relatedProjects) {
    this.relatedProjects = relatedProjects;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10408")
  public List<String> getIndustrySector() {
    return industrySector;
  }

  public void setIndustrySector(List<String> industrySector) {
    this.industrySector = industrySector;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10500")
  public CustomFieldOption getDevlDeadline() {
    return devlDeadline;
  }

  public void setDevlDeadline(CustomFieldOption devlDeadline) {
    this.devlDeadline = devlDeadline;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10501")
  public Integer getSecondEstimation() {
    return secondEstimation;
  }

  public void setSecondEstimation(Integer secondEstimation) {
    this.secondEstimation = secondEstimation;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10502")
  public String getTestScriptIds() {
    return testScriptIds;
  }

  public void setTestScriptIds(String testScriptIds) {
    this.testScriptIds = testScriptIds;
  }


  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10503")
  public CustomFieldOption getTimeSlot() {
    return timeSlot;
  }

  public void setTimeSlot(CustomFieldOption timeSlot) {
    this.timeSlot = timeSlot;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10505", nillable = true)
  public Integer getSiloRanking() {
    return siloRanking;
  }

  public void setSiloRanking(Integer siloRanking) {
    this.siloRanking = siloRanking;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10506")
  public JiraUser getProductOwner() {
    return productOwner;
  }

  public void setProductOwner(JiraUser productOwner) {
    this.productOwner = productOwner;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10507")
  public JiraRadioItem getRoadmap() {
    return roadmap;
  }

  public void setRoadmap(JiraRadioItem roadmap) {
    this.roadmap = roadmap;
  }


  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10508")
  public CustomFieldOption getCustomer() {
    return customer;
  }

  public void setCustomer(CustomFieldOption customer) {
    this.customer = customer;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10509")
  public CustomFieldOption getRealisedByIDM() {
    return realisedByIDM;
  }

  public void setRealisedByIDM(CustomFieldOption realisedByIDM) {
    this.realisedByIDM = realisedByIDM;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10510")
  public CustomFieldOption getImplementationLevel() {
    return implementationLevel;
  }

  public void setImplementationLevel(CustomFieldOption implementationLevel) {
    this.implementationLevel = implementationLevel;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10700")
  public String getFiledAgainst() {
    return filedAgainst;
  }

  public void setFiledAgainst(String filedAgainst) {
    this.filedAgainst = filedAgainst;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10701")
  public String getPlannedFor() {
    return plannedFor;
  }

  public void setPlannedFor(String plannedFor) {
    this.plannedFor = plannedFor;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10009")
  public String getEpicLink() {
    return epicLink;
  }

  public void setEpicLink(String epicLink) {
    this.epicLink = epicLink;
  }

  public Issue getParent() {
    return parent;
  }

  public void setParent(Issue parent) {
    this.parent = parent;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10008")
  public String getEpicName() {
    return epicName;
  }

  public void setEpicName(String epicName) {
    this.epicName = epicName;
  }

  public List<Version> getFixVersions() {
    return fixVersions;
  }

  public void setFixVersions(List<Version> versions) {
    this.fixVersions = versions;
  }


}
