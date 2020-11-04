package com.adobe.blueapp.service.imsgraph.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author siddgupt
 *
 */
@ConfigurationProperties("imsgraph")
public class IMSGraphProperties {

  private String host;
  private String usersEndpoint;
  private String rolesEndpoint;

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

  public String getRolesEndpoint() {
    return rolesEndpoint;
  }

  public void setRolesEndpoint(String rolesEndpoint) {
    this.rolesEndpoint = rolesEndpoint;
  }

}
