package com.adobe.blueapp.service.jil.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author siddgupt
 *
 */
@ConfigurationProperties("jil")
public class JilProperties {

  private String host;
  private String directoryEndpoint;
  private String imsClientId;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getDirectoryEndpoint() {
    return directoryEndpoint;
  }

  public void setDirectoryEndpoint(String directoryEndpoint) {
    this.directoryEndpoint = directoryEndpoint;
  }

  public String getImsClientId() {
    return imsClientId;
  }

  public void setImsClientId(String imsClientId) {
    this.imsClientId = imsClientId;
  }
}
