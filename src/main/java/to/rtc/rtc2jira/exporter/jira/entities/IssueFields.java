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
  private int storyPoints;
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
  public int getStoryPoints() {
    return storyPoints;
  }

  public void setStoryPoints(int storyPoints) {
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
  @XmlElement(name = "customfield_10401")
  public Integer getEpReqNr() {
    return epReqNr;
  }

  public void setEpReqNr(Integer epReqNr) {
    this.epReqNr = epReqNr;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10402")
  public Integer getEpReqNrUg() {
    return epReqNrUg;
  }

  public void setEpReqNrUg(Integer epReqNrUg) {
    this.epReqNrUg = epReqNrUg;
  }

  @JsonView(IssueView.Update.class)
  @XmlElement(name = "customfield_10403")
  public Integer getDpReqNr() {
    return dpReqNr;
  }

  public void setDpReqNr(Integer dpReqNr) {
    this.dpReqNr = dpReqNr;
  }


}
