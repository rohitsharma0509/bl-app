package com.adobe.blueapp.service.ims.dto;

/**
 * 
 * @author siddgupt
 *
 */
public class ServicePrincipal {
  private String organizationId;
  private String clientId;
  private String servicePrincipalId;
  private String allowedScopes;

  public String getOrganizationId() {
    return organizationId;
  }

  public String getClientId() {
    return clientId;
  }

  public String getServicePrincipalId() {
    return servicePrincipalId;
  }

  public String getAllowedScopes() {
    return allowedScopes;
  }

  public void setOrganizationId(String organizationId) {
    this.organizationId = organizationId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public void setServicePrincipalId(String servicePrincipalId) {
    this.servicePrincipalId = servicePrincipalId;
  }

  public void setAllowedScopes(String allowedScopes) {
    this.allowedScopes = allowedScopes;
  }
}
