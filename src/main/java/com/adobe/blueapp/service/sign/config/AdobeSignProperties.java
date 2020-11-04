package com.adobe.blueapp.service.sign.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author siddgupt
 *
 */
@ConfigurationProperties("adobesign")
public class AdobeSignProperties {
  private String host;
  private String baseUriEndpoint;
  private String userViewsEndpoint;
  private String agreementEndpoint;
  private String agreementViewsEndpoint;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getBaseUriEndpoint() {
    return baseUriEndpoint;
  }

  public void setBaseUriEndpoint(String baseUriEndpoint) {
    this.baseUriEndpoint = baseUriEndpoint;
  }

  public String getUserViewsEndpoint() {
    return userViewsEndpoint;
  }

  public void setUserViewsEndpoint(String userViewsEndpoint) {
    this.userViewsEndpoint = userViewsEndpoint;
  }

  public String getAgreementEndpoint() {
    return agreementEndpoint;
  }

  public void setAgreementEndpoint(String agreementEndpoint) {
    this.agreementEndpoint = agreementEndpoint;
  }

  public String getAgreementViewsEndpoint() {
    return agreementViewsEndpoint;
  }

  public void setAgreementViewsEndpoint(String agreementViewsEndpoint) {
    this.agreementViewsEndpoint = agreementViewsEndpoint;
  }

}
