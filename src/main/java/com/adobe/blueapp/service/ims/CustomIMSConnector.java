/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 * Copyright 2019 Adobe
 * All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains
 * the property of Adobe and its suppliers, if any. The intellectual
 * and technical concepts contained herein are proprietary to Adobe
 * and its suppliers and are protected by all applicable intellectual
 * property laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe.
 **************************************************************************/

package com.adobe.blueapp.service.ims;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.adobe.asr.connector.ims.IMSConnector;
import com.adobe.asr.connector.ims.dto.Organization;
import com.adobe.asr.connector.ims.exception.IMSConnectorException;
import com.adobe.blueapp.constants.ErrorCodes;
import com.adobe.blueapp.exception.ServiceException;
import com.adobe.blueapp.restclient.WebClient;
import com.adobe.blueapp.restclient.WebClientRequest;
import com.adobe.blueapp.service.ims.config.IMSClientProperties;
import com.adobe.blueapp.service.ims.config.IMSProperties;
import com.adobe.blueapp.service.ims.dto.IMSAccessToken;
import com.adobe.blueapp.service.ims.dto.ServicePrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Wrapper over {@link IMSConnector}
 *
 * @author siddgupt
 */

public class CustomIMSConnector {

  public CustomIMSConnector(IMSProperties imsProperties, IMSClientProperties imsClientProperties) {
    this.imsProperties = imsProperties;
    this.imsClientProperties = imsClientProperties;
  }

  public String getServiceToken() {
    try {
      return imsConnector.generateServiceToken(imsClientProperties.getClientId(), imsClientProperties.getClientSecret(), imsClientProperties.getClientOauthCode());
    }
    catch (IMSConnectorException e) {
      throwNewIMSException("Exception while generating service token", e.getHttpStatusCode(), e);
    }
    catch (Exception e) {
      throwNewIMSException("Exception while generating service token", Status.INTERNAL_SERVER_ERROR, e);
    }
    return null;
  }

  public List<Organization> getOrganizations(String accessToken) {
    try {
      return imsConnector.getOrganizationDetails(imsClientProperties.getClientId(), accessToken);
    }
    catch (IMSConnectorException e) {
      throwNewIMSException("Exception while getting organizations", e.getHttpStatusCode(), e);
    }
    catch (Exception e) {
      throwNewIMSException("Exception while getting organizations", Status.INTERNAL_SERVER_ERROR, e);
    }
    return null;
  }

  public IMSAccessToken getExtendedAccessToken(String serviceToken, String accessToken, String scopes) {
    String msg = "Error while creating extended access token";

    HashMap<String, String> headersMap = new HashMap<>();
    headersMap.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED);
    headersMap.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

    Form form = new Form();
    form.param("client_id", imsClientProperties.getClientId());
    form.param("client_secret", imsClientProperties.getClientSecret());
    form.param("access_token", accessToken);
    form.param("service_token", serviceToken);
    form.param("scope", scopes);

    Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

    WebClientRequest request = new WebClientRequest(HttpMethod.POST, imsProperties.getUrl(), imsProperties.getExtendedTokenPath(),
        headersMap, entity);

    return imsWebClient.exchange(request, IMSAccessToken.class, msg);
  }

  public ServicePrincipal createServicePrincipal(String accessToken, String orgId, String scopes) {
    String msg = "Error while creating service principal";

    HashMap<String, String> headersMap = new HashMap<>();
    headersMap.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED);
    headersMap.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
    headersMap.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

    Form form = new Form();
    form.param("client_id", imsClientProperties.getClientId());
    form.param("org_id", orgId);
    form.param("binding_client_id", imsClientProperties.getClientId());
    form.param("scope", scopes);

    Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

    WebClientRequest request = new WebClientRequest(HttpMethod.POST, imsProperties.getUrl(), imsProperties
        .getServicePrincipalsPath(), headersMap, entity);

    try {
      return imsWebClient.exchange(request, ServicePrincipal.class, msg);
    }
    catch (ServiceException e) {
      if (e.getStatus() == Status.CONFLICT.getStatusCode()) {
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setAll(headersMap);

        StringBuilder queryparams = new StringBuilder();
        queryparams.append("client_id=").append(imsClientProperties.getClientId());
        queryparams.append("&org_id=").append(orgId);
        queryparams.append("&binding_client_id=").append(imsClientProperties.getClientId());
        queryparams.append("&scope=").append(scopes);

        HttpEntity<MultiValueMap<String, String>> httpentity = new HttpEntity<>(headers);

        ResponseEntity<ServicePrincipal> response = restTemplate.exchange(request.getUrl() + request.getPath()
                                                                          + "?"
                                                                          + queryparams,
                                                                          org.springframework.http.HttpMethod.PATCH,
                                                                          httpentity, ServicePrincipal.class);
        return response.getBody();
        /**
         * request.setHttpMethod(HttpMethod.PATCH);
         * return exchange(request, ServicePrincipal.class, msg);
         */
      }
      throw e;
    }
  }

  public IMSAccessToken getClientCredentialsToken(String orgId, String scopes) {
    String msg = "Error while generating client_credentials token";

    HashMap<String, String> headersMap = new HashMap<>();
    headersMap.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED);
    headersMap.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

    Form form = new Form();
    form.param("grant_type", "client_credentials");
    form.param("client_id", imsClientProperties.getClientId());
    form.param("client_secret", imsClientProperties.getClientSecret());
    form.param("org_id", orgId);
    if (StringUtils.isNotBlank(scopes)) {
      form.param("scope", scopes);
    }

    Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

    WebClientRequest request = new WebClientRequest(HttpMethod.POST, imsProperties.getUrl(), imsProperties
        .getTokenEndpoint(), headersMap, entity);

    return imsWebClient.exchange(request, IMSAccessToken.class, msg);
  }

  public IMSAccessToken generateAccessTokenFromAuthCode(String authCode, String scopes) {
    String msg = "Error while generating access_token";

    HashMap<String, String> headersMap = new HashMap<>();
    headersMap.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED);
    headersMap.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

    Form form = new Form();
    form.param("grant_type", "authorization_code");
    form.param("client_id", imsClientProperties.getClientId());
    form.param("client_secret", imsClientProperties.getClientSecret());
    form.param("code", authCode);
    if (StringUtils.isNotBlank(scopes)) {
      form.param("scope", scopes);
    }

    Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

    WebClientRequest request = new WebClientRequest(HttpMethod.POST, imsProperties.getUrl(), imsProperties
        .getTokenEndpoint(), headersMap, entity);

    return imsWebClient.exchange(request, IMSAccessToken.class, msg);
  }

  public IMSAccessToken generatePoseAsToken(String accessToken, String userId, String scopes) {
    String msg = "Error while generating poseas token";

    HashMap<String, String> headersMap = new HashMap<>();
    headersMap.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED);
    headersMap.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

    Form form = new Form();
    form.param("grant_type", "pose_as");
    form.param("client_id", imsClientProperties.getClientId());
    form.param("client_secret", imsClientProperties.getClientSecret());
    form.param("authenticating_token", accessToken);
    form.param("user_id", userId);
    if (StringUtils.isNotBlank(scopes)) {
      form.param("scope", scopes);
    }

    Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

    WebClientRequest request = new WebClientRequest(HttpMethod.POST, imsProperties.getUrl(), imsProperties
        .getTokenEndpoint(), headersMap, entity);

    return imsWebClient.exchange(request, IMSAccessToken.class, msg);
  }

  private void throwNewIMSException(String message, Status status, Exception e) throws ServiceException {
    throwNewIMSException(message, status.getStatusCode(), e);
  }

  private void throwNewIMSException(String message, int statuscode, Exception e) throws ServiceException {
    throw new ServiceException(message, e).withCode(ErrorCodes.IMS_ERROR).withStatus(statuscode);
  }

  private final IMSClientProperties imsClientProperties;
  private final IMSProperties imsProperties;

  @Autowired
  private IMSConnector imsConnector;

  @Autowired
  private WebClient imsWebClient;

  @Autowired
  private RestTemplate restTemplate;

}