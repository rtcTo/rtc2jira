package to.rtc.rtc2jira.exporter.jira;

import java.util.Base64;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import to.rtc.rtc2jira.exporter.jira.entities.IssueView;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.FormDataMultiPart;

/**
 * Little helper class, which is responsible for all gets/post to the JIRA REST API
 * 
 * @author Manuel
 */
public class JiraRestAccess {

  private Client client;
  private ObjectMapper objectMapper;
  private String authentification;
  private String restHome;

  private static final String JIRA_REST_API_SUFFIX = "/rest/api/2";

  JiraRestAccess(String url, String user, String password) {
    this.restHome = url + JIRA_REST_API_SUFFIX;
    String userAndPassword = user + ':' + password;
    this.authentification = new String(Base64.getEncoder().encode(userAndPassword.getBytes()));

    JacksonJsonProvider jacksonJsonProvider =
        new JacksonJaxbJsonProvider().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper = jacksonJsonProvider.locateMapper(Object.class, MediaType.APPLICATION_JSON_TYPE);

    ClientConfig cfg = new DefaultClientConfig();
    cfg.getSingletons().add(jacksonJsonProvider);
    this.client = Client.create(cfg);
    // this.client.addFilter(new LoggingFilter(System.out));
  }

  public <T> T get(String resource, GenericType<T> type) {
    ClientResponse response = get(resource);
    return response.getEntity(type);
  }

  public <T> T get(String resource, Class<T> type) {
    ClientResponse response = get(resource);
    return response.getEntity(type);
  }

  public ClientResponse postMultiPart(String ressource, FormDataMultiPart multiPart) {
    WebResource webResource = client.resource(restHome + ressource);

    Builder responseBuilder =
        webResource.header("Authorization", "Basic " + authentification).header("X-Atlassian-Token", "nocheck")
            .type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON);


    return responseBuilder.post(ClientResponse.class, multiPart);
  }

  public <T> T post(String resource, T toPostingObject, Class<T> responseType) {
    ClientResponse postResponse = post(resource, toPostingObject);
    return postResponse.getEntity(responseType);
  }

  public ClientResponse get(String resource) {
    objectMapper.setSerializationConfig(objectMapper.getSerializationConfig().withView(IssueView.Read.class));
    return createJsonResponseBuilder(resource).get(ClientResponse.class);
  }

  public ClientResponse post(String ressource, Object toPostingObject) {
    objectMapper.setSerializationConfig(objectMapper.getSerializationConfig().withView(IssueView.Create.class));
    return createJsonResponseBuilder(ressource).post(ClientResponse.class, toPostingObject);
  }

  public ClientResponse put(String ressource, Object objectToPut) {
    objectMapper.setSerializationConfig(objectMapper.getSerializationConfig().withView(IssueView.Update.class));
    return createJsonResponseBuilder(ressource).put(ClientResponse.class, objectToPut);
  }

  private Builder createJsonResponseBuilder(String resource) {
    WebResource webResource = client.resource(restHome + resource);
    Builder responseBuilder =
        webResource.header("Authorization", "Basic " + authentification).type(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
    return responseBuilder;
  }
}
