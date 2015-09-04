/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.storage;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
  private static final long serialVersionUID = 1L;
  private final String createrName;
  private String creatorEmail;
  private final Date date;
  private final String comment;
  private String jiraId;

  public Comment(String createrName, String creatorEmail, Date date, String comment) {
    this.createrName = createrName;
    this.creatorEmail = creatorEmail;
    this.date = date;
    this.comment = comment;
  }

  public String getCreaterName() {
    return createrName;
  }

  public String getCreatorEmail() {
    return creatorEmail;
  }

  public Date getDate() {
    return date;
  }

  public String getComment() {
    return comment;
  }

  public String getJiraId() {
    return jiraId;
  }

  public void setJiraId(String jiraId) {
    this.jiraId = jiraId;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || !(obj instanceof Comment)) {
      return false;
    }
    Comment comparand = (Comment) obj;
    return comparand.getDate().equals(this.getDate()) && comparand.getCreatorEmail().equals(this.getCreatorEmail());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (this.date == null ? 0 : this.date.hashCode());
    result = prime * result + (this.creatorEmail == null ? 0 : this.creatorEmail.hashCode());
    return result;
  }
}
