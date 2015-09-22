package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Timetracking {

  String originalEstimate;
  String remainingEstimate;
  int originalEstimateSeconds;
  int remainingEstimateSeconds;

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

  public int getOriginalEstimateSeconds() {
    return originalEstimateSeconds;
  }

  public void setOriginalEstimateSeconds(int originalEstimateSeconds) {
    this.originalEstimateSeconds = originalEstimateSeconds;
  }

  public int getRemainingEstimateSeconds() {
    return remainingEstimateSeconds;
  }

  public void setRemainingEstimateSeconds(int remainingEstimateSeconds) {
    this.remainingEstimateSeconds = remainingEstimateSeconds;
  }
}
