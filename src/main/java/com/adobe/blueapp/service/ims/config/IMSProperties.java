package com.adobe.blueapp.service.ims.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author siddgupt
 *
 */
@ConfigurationProperties("ims")
public class IMSProperties {

  private String url;
  private String extendedTokenPath;
  private String servicePrincipalsPath;
  private String tokenEndpoint;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getExtendedTokenPath() {
    return extendedTokenPath;
  }

  public void setExtendedTokenPath(String extendedTokenPath) {
    this.extendedTokenPath = extendedTokenPath;
  }

  public String getServicePrincipalsPath() {
    return servicePrincipalsPath;
  }

  public void setServicePrincipalsPath(String servicePrincipalsPath) {
    this.servicePrincipalsPath = servicePrincipalsPath;
  }

  public String getTokenEndpoint() {
    return tokenEndpoint;
  }

  public void setTokenEndpoint(String tokenEndpoint) {
    this.tokenEndpoint = tokenEndpoint;
  }

}
