package com.adobe.blueapp.service.soup;

import java.util.HashMap;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.adobe.blueapp.restclient.WebClient;
import com.adobe.blueapp.restclient.WebClientRequest;
import com.adobe.blueapp.service.soup.config.SoupProperties;
import com.adobe.blueapp.service.soup.dto.SoupUserProvisionRequest;
import com.adobe.blueapp.service.soup.dto.SoupUserProvisionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author siddgupt
 *
 */
@Service
public class SoupConnector {

  public SoupUserProvisionResponse createUser(String accessToken, SoupUserProvisionRequest soupUserProvisionRequest) {
    String msg = "Error while creating user in Sign";

    HashMap<String, String> headersMap = new HashMap<>();
    headersMap.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

    Entity<SoupUserProvisionRequest> entity = Entity.entity(soupUserProvisionRequest, MediaType.APPLICATION_JSON);

    WebClientRequest request = new WebClientRequest(HttpMethod.POST, soupProperties.getHost(), soupProperties.getUsersEndpoint(), headersMap, entity);

    return soupWebClient.exchange(request, SoupUserProvisionResponse.class, msg);

  }

  @Autowired
  private WebClient soupWebClient;
  @Autowired
  private SoupProperties soupProperties;

}
