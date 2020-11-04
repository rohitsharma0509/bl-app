package com.adobe.blueapp.service.sign;

import java.util.HashMap;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.adobe.blueapp.restclient.WebClient;
import com.adobe.blueapp.restclient.WebClientRequest;
import com.adobe.blueapp.service.sign.config.AdobeSignProperties;
import com.adobe.blueapp.service.sign.dto.AgreementCreationResponse;
import com.adobe.blueapp.service.sign.dto.AgreementInfo;
import com.adobe.blueapp.service.sign.dto.AgreementViewResponse;
import com.adobe.blueapp.service.sign.dto.BaseUriInfo;
import com.adobe.blueapp.service.sign.dto.UserViewInfoRequest;
import com.adobe.blueapp.service.sign.dto.UserViewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author siddgupt
 *
 */
@Service
public class SignConnector {

  public BaseUriInfo getBaseUriInfo(String accessToken) {
    String msg = "Error while getting BaseUriInfo";

    HashMap<String, String> headersMap = new HashMap<>();
    headersMap.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    headersMap.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

    WebClientRequest request = new WebClientRequest(HttpMethod.GET, adobeSignProperties.getHost(), adobeSignProperties.getBaseUriEndpoint(), headersMap);

    return signWebClient.exchange(request, BaseUriInfo.class, msg);

  }

  public UserViewResponse getUserViews(BaseUriInfo baseUriInfo, String accessToken, String userId, UserViewInfoRequest userViewInfoRequest) {
    String msg = "Error while getting user views";

    HashMap<String, String> headersMap = new HashMap<>();
    headersMap.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    headersMap.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
    headersMap.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

    Entity<UserViewInfoRequest> entity = Entity.entity(userViewInfoRequest, MediaType.APPLICATION_JSON);
    String path = String.format(adobeSignProperties.getUserViewsEndpoint(), userId);

    WebClientRequest request = new WebClientRequest(HttpMethod.POST, baseUriInfo.getApiAccessPoint(), path, headersMap, entity);

    return signWebClient.exchange(request, UserViewResponse.class, msg);
  }

  public AgreementViewResponse getComposePage(BaseUriInfo baseUriInfo, String accessToken) {
    String msg = "Error while getting compose page";

    HashMap<String, String> headersMap = new HashMap<>();
    headersMap.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    headersMap.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
    headersMap.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

    AgreementInfo agreementInfo = new AgreementInfo();
    agreementInfo.setState("DRAFT");

    Entity<AgreementInfo> entity = Entity.entity(agreementInfo, MediaType.APPLICATION_JSON);

    WebClientRequest request = new WebClientRequest(HttpMethod.POST, baseUriInfo.getApiAccessPoint(), adobeSignProperties.getAgreementEndpoint(), headersMap, entity);

    AgreementCreationResponse agreementCreationResponse = signWebClient.exchange(request, AgreementCreationResponse.class, msg);

    UserViewInfoRequest userViewInfoRequest = new UserViewInfoRequest();
    userViewInfoRequest.setName("ALL");
    UserViewInfoRequest.CommonViewConfiguration commonViewConfiguration = new UserViewInfoRequest.CommonViewConfiguration();
    commonViewConfiguration.setAutoLoginUser(true);
    commonViewConfiguration.setNoChrome(true);
    userViewInfoRequest.setCommonViewConfiguration(commonViewConfiguration);

    Entity<UserViewInfoRequest> viewentity = Entity.entity(userViewInfoRequest, MediaType.APPLICATION_JSON);
    String path = String.format(adobeSignProperties.getAgreementViewsEndpoint(), agreementCreationResponse.getId());

    request = new WebClientRequest(HttpMethod.POST, baseUriInfo.getApiAccessPoint(), path, headersMap, viewentity);

    return signWebClient.exchange(request, AgreementViewResponse.class, msg);

  }

  @Autowired
  private WebClient signWebClient;
  @Autowired
  private AdobeSignProperties adobeSignProperties;
}
