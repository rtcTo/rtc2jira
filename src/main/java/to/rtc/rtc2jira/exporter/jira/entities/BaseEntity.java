package to.rtc.rtc2jira.exporter.jira.entities;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlRootElement;

import to.rtc.rtc2jira.exporter.jira.JiraPersistence;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;

@XmlRootElement
public abstract class BaseEntity {
  private static final Logger LOGGER = Logger.getLogger(BaseEntity.class.getName());

  private String id;
  private String key;
  private URL self;


  protected BaseEntity() {}

  protected BaseEntity(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public URL getSelf() {
    return self;
  }

  public void setSelf(URL self) {
    this.self = self;
  }

  BaseEntity createEntityInJira() {
    ClientResponse postResponse = JiraPersistence.getInstance().getRestAccess().post(getPath(), this);
    if (postResponse.getStatus() == Status.CREATED.getStatusCode()) {
      return postResponse.getEntity(this.getClass());
    } else {
      String message = "Problems while creating entity: " + postResponse.getEntity(String.class);
      System.err.println(message);
      LOGGER.log(Level.WARNING, message);
      return null;
    }
  }

  private boolean updateEntityInJira() {
    ClientResponse postResponse =
        JiraPersistence.getInstance().getRestAccess().put(getPath() + "/" + this.getKey(), this);
    if (postResponse.getStatus() >= Status.OK.getStatusCode()
        && postResponse.getStatus() <= Status.PARTIAL_CONTENT.getStatusCode()) {
      return true;
    } else {
      String message = "Problems while updating entity: " + postResponse.getEntity(String.class);
      System.err.println(message);
      LOGGER.log(Level.WARNING, message);
      return false;
    }
  }



  abstract public String getPath();

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

}
