package cm.busime.camerpay.restclient;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class RestClientProducer {
  public static final String CMR_PAY_SERVICE = "EVE_VR_SERVICE";

  @Produces
  public RestClient createRestClient(final InjectionPoint injectionPoint) {
    final RestClientConfiguration annotation = injectionPoint.getAnnotated().getAnnotation(RestClientConfiguration.class);
    if (CMR_PAY_SERVICE.equals(annotation.externalService())) {
      final String baseUrl = "http://localhost:8080/camerpay-api/";
      final String apisecUsername = "dougladdws";
      final String apisecPassword = "12345567890";
      return new RestClient(baseUrl, apisecUsername, apisecPassword, annotation.gzipSupport());
    }
    return null;
  }
}