package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Timetracking {

  String originalEstimate;
  String remainingEstimate;
  long originalEstimateSeconds;
  long remainingEstimateSeconds;

  public String getOriginalEstimate() {
    return originalEstimate;
  }

  public void setOriginalEstimate(String originalEstimate) {
    this.originalEstimate = originalEstimate;
  }

  public String getRemainingEstimate() {
    return remainingEstimate;
  }

  public void setRemainingEstimate(String remainingEstimate) {
    this.remainingEstimate = remainingEstimate;
  }

  public long getOriginalEstimateSeconds() {
    return originalEstimateSeconds;
  }

  public void setOriginalEstimateSeconds(long originalEstimateSeconds) {
    this.originalEstimateSeconds = originalEstimateSeconds;
  }

  public long getRemainingEstimateSeconds() {
    return remainingEstimateSeconds;
  }

  public void setRemainingEstimateSeconds(long remainingEstimateSeconds) {
    this.remainingEstimateSeconds = remainingEstimateSeconds;
  }
}
