package to.rtc.rtc2jira.exporter.jira;

import java.util.Base64;

import javax.ws.rs.core.MediaType;

import org.apache.http.auth.AuthenticationException;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Little helper class, which is responsible for all gets/post to the JIRA REST API
 * 
 * @author Manuel
 */
public class JiraRestAccess {

  private Client client;
  private String authentification;
  private String restHome;

  private static final String JIRA_REST_API_SUFFIX = "/rest/api/2";

  JiraRestAccess(String url, String user, String password) {
    this.restHome = url + JIRA_REST_API_SUFFIX;
    String userAndPassword = user + ':' + password;
    this.authentification = new String(Base64.getEncoder().encode(userAndPassword.getBytes()));;
    ClientConfig cfg = new DefaultClientConfig();
    cfg.getClasses().add(JacksonJsonProvider.class);
    this.client = Client.create(cfg);
  }

  <T> T get(String resource, GenericType<T> type) {
    ClientResponse response = getResponse(resource);
    return response.getEntity(type);
  }

  <T> T get(String resource, Class<T> type) {
    ClientResponse response = getResponse(resource);
    return response.getEntity(type);
  }

  <T> T post(String resource, Object toPostingObject, Class<T> type)
      throws AuthenticationException, ClientHandlerException {
    ClientResponse post =
        createJsonResponseBuilder(resource).post(ClientResponse.class, toPostingObject);
    return post.getEntity(type);
  }

  ClientResponse getResponse(String resource) {
    return createJsonResponseBuilder(resource).get(ClientResponse.class);
  }

  private Builder createJsonResponseBuilder(String resource) {
    WebResource webResource = client.resource(restHome + resource);
    Builder responseBuilder = webResource.header("Authorization", "Basic " + authentification)
        .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
    return responseBuilder;
  }
}
