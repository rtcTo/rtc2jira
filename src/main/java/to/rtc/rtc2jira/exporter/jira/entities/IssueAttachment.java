package to.rtc.rtc2jira.exporter.jira.entities;

import java.net.URL;

import org.codehaus.jackson.map.annotate.JsonView;

public class IssueAttachment extends BaseEntity {

  private JiraUser author;
  private String filename;
  private int size;
  private String mimeType;
  private URL content;
  private URL thumbnail;

  @Override
  public String getPath() {
    return "/attachment";
  }

  @Override
  public String getSelfPath() {
    return getPath() + "/" + getId();
  }

  @JsonView(IssueView.Read.class)
  public JiraUser getAuthor() {
    return author;
  }

  public void setAuthor(JiraUser author) {
    this.author = author;
  }

  @JsonView(IssueView.Read.class)
  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  @JsonView(IssueView.Read.class)
  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  @JsonView(IssueView.Read.class)
  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  @JsonView(IssueView.Read.class)
  public URL getContent() {
    return content;
  }

  public void setContent(URL content) {
    this.content = content;
  }

  @JsonView(IssueView.Read.class)
  public URL getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(URL thumbnail) {
    this.thumbnail = thumbnail;
  }

}
