package com.adobe.blueapp.service.soup.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author siddgupt
 *
 */
@ConfigurationProperties("soup")
public class SoupProperties {

  private String host;
  private String usersEndpoint;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getUsersEndpoint() {
    return usersEndpoint;
  }

  public void setUsersEndpoint(String usersEndpoint) {
    this.usersEndpoint = usersEndpoint;
  }

}
