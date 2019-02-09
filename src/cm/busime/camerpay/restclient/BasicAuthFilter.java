package cm.busime.camerpay.restclient;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class BasicAuthFilter implements ClientRequestFilter {

  private final String user;
  private final String password;

  public BasicAuthFilter(String user, String password) {
    this.user = user;
    this.password = password;
  }

  public void filter(ClientRequestContext requestContext) throws IOException {
    requestContext.getHeaders().add("Authorization", getBasicAuthentication());
  }

  private String getBasicAuthentication() {
    String token = this.user + ":" + this.password;
    try {
      return "BASIC " + DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException ex) {
      throw new IllegalStateException("Cannot encode with UTF-8", ex);
    }
  }
}
