package cm.busime.camerpay.restclient;

import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.message.GZipEncoder;

import cm.busime.camerpay.profiling.ExternalSystemType;
import cm.busime.camerpay.profiling.Profile;


public class RestClient {
	
	private static Logger log = Logger.getLogger(RestClient.class.getName());
	
	  private static final Integer DEFAULT_TIMEOUT = 10000;

	  private final String baseUrl;
	  private Client client;

	  RestClient(final String baseUrl,
	             final String apisecUsername,
	             final String apisecPassword,
	             final boolean gzipSupport) {
	    this(baseUrl, apisecUsername, apisecPassword, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, gzipSupport);
	  }

	  RestClient(final String baseUrl,
	             final String apisecUsername,
	             final String apisecPassword,
	             final Integer readTimeout,
	             final Integer connectTimeout,
	             final boolean gzipSupport) {
	    if (baseUrl == null) {
	      throw new IllegalArgumentException("baseUrl == null");
	    }
	    this.baseUrl = baseUrl;
	    this.client = ClientBuilder.newClient();
	    this.client.property(ClientProperties.READ_TIMEOUT, readTimeout == null ? DEFAULT_TIMEOUT : readTimeout);
	    this.client.property(ClientProperties.CONNECT_TIMEOUT, connectTimeout == null ? DEFAULT_TIMEOUT : connectTimeout);

	    if (gzipSupport) {
	      this.client.register(new GZipEncoder());
	    }

	    if (apisecUsername != null && !apisecUsername.isEmpty() && apisecPassword != null && !apisecPassword.isEmpty()) {
	      client.register(new BasicAuthFilter(apisecUsername, apisecPassword));
	    }
	  }

	  public String getBaseUrl() {
	    return baseUrl;
	  }

	  @Profile(ExternalSystemType.HTTP)
	  public Response post(String resource, Object request, Integer connectTimeout, Integer readTimeout) {
	    Response clientResponse = null;
	    String url = serviceUrl(resource);
	    long start = System.currentTimeMillis();
	    readTimeout = readTimeout == null ? DEFAULT_TIMEOUT : readTimeout;
	    connectTimeout = connectTimeout == null ? DEFAULT_TIMEOUT : connectTimeout;
	    Entity<Object> entityRequest = Entity.entity(request, MediaType.APPLICATION_JSON);
	    log.log(Level.INFO, "entityRequest: " + entityRequest.toString());
	    log.log(Level.INFO, "url: " + url);
	    try {
	      clientResponse = client
	          .property(ClientProperties.READ_TIMEOUT, readTimeout)
	          .property(ClientProperties.CONNECT_TIMEOUT, connectTimeout)
	          .target(url)
	          .request(MediaType.APPLICATION_JSON)
	          .accept(MediaType.APPLICATION_JSON)
	          .post(entityRequest, Response.class);

	    } catch (final Exception e) {
	    	log.log(Level.INFO, "post request failed: " + e.getMessage());
	      if (e.getCause() instanceof SocketTimeoutException) {
	        // service timeout - must be returned 424 Dependency failed status code
	        long time = System.currentTimeMillis() - start;
	        final Object[] info = {
	            (connectTimeout / 1000),
	            (readTimeout / 1000),
	            time
	        };
	        log.log(Level.SEVERE, e.getMessage());
	        //throw new EmissionException(ErrorCode.EVE_VR_SERVICE_TIMEOUT, e, info);
	      }
	      log.log(Level.SEVERE, e.getMessage());
	      //throw new EmissionException(ErrorCode.EXTERNAL_SERVICE_COMMUNICATION_ERROR, e, url, "background communication error");
	    }

	    if (clientResponse == null) {
	    	log.log(Level.INFO, "Backend communication error: POST url=" + url + ", response is null.");
	    }
	    return clientResponse;
	  }
	  
	  @Profile(ExternalSystemType.HTTP)
	  public Response put(String resource, JsonObject request, Integer connectTimeout, Integer readTimeout) {
	    Response clientResponse = null;
	    String url = serviceUrl(resource);
	    long start = System.currentTimeMillis();
	    readTimeout = readTimeout == null ? DEFAULT_TIMEOUT : readTimeout;
	    connectTimeout = connectTimeout == null ? DEFAULT_TIMEOUT : connectTimeout;
	    log.log(Level.INFO, "PUT request to " + url);
	    try {
	      clientResponse = client
	          .property(ClientProperties.READ_TIMEOUT, readTimeout)
	          .property(ClientProperties.CONNECT_TIMEOUT, connectTimeout)
	          .target(url)
	          .request(MediaType.APPLICATION_JSON)
	          .accept(MediaType.APPLICATION_JSON)
	          .put(Entity.json(request.toString()));

	    } catch (final Exception e) {
	      if (e.getCause() instanceof SocketTimeoutException) {
	        // service timeout - must be returned 424 Dependency failed status code
	        long time = System.currentTimeMillis() - start;
	        final Object[] info = {
	            (connectTimeout / 1000),
	            (readTimeout / 1000),
	            time
	        };
	        log.log(Level.SEVERE, e.getMessage());
	        //throw new EmissionException(ErrorCode.EVE_VR_SERVICE_TIMEOUT, e, info);
	      }
	      log.log(Level.SEVERE, e.getMessage());
	      //throw new EmissionException(ErrorCode.EXTERNAL_SERVICE_COMMUNICATION_ERROR, e, url, "background communication error");
	    }

	    if (clientResponse == null) {
	    	log.log(Level.INFO, "Backend communication error: POST url=" + url + ", response is null.");
	    }
	    return clientResponse;
	  }

	  private String serviceUrl(final String resourcePath) {
	    String url = baseUrl;
	    if (url != null && resourcePath != null) {
	      if (url.endsWith("/") && resourcePath.startsWith("/")) {
	        url = url.substring(0, url.length() - 1);
	      }
	      url += resourcePath;
	    }
	    return url;
	  }
}
