package com.adobe.blueapp.service.adminsdk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author siddgupt
 *
 */
@ConfigurationProperties("adminsdk")
public class AdminSDKProperties {

  private String host;
  private String productProfileEndpoint;
  private String imsClientId;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getProductProfileEndpoint() {
    return productProfileEndpoint;
  }

  public void setProductProfileEndpoint(String productProfileEndpoint) {
    this.productProfileEndpoint = productProfileEndpoint;
  }

  public String getImsClientId() {
    return imsClientId;
  }

  public void setImsClientId(String imsClientId) {
    this.imsClientId = imsClientId;
  }
}
