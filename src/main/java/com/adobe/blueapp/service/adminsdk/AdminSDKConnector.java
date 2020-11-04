package com.adobe.blueapp.service.adminsdk;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.HttpHeaders;

import com.adobe.blueapp.constants.CustomHttpHeaders;
import com.adobe.blueapp.restclient.WebClient;
import com.adobe.blueapp.restclient.WebClientRequest;
import com.adobe.blueapp.service.adminsdk.config.AdminSDKProperties;
import com.adobe.blueapp.service.adminsdk.dto.ProductProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author siddgupt
 *
 */
@Service
public class AdminSDKConnector {

  public ProductProfile[] getProductProfiles(String accessToken) {
    String msg = "Error while fetching product profiles";

    HashMap<String, String> headersMap = new HashMap<>();
    headersMap.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    headersMap.put(HttpHeaders.ACCEPT, "application/vnd.adobe.sign.profiles.2020.1+json");
    headersMap.put(CustomHttpHeaders.X_API_KEY, adminSDKProperties.getImsClientId());

    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("filters", "sign");

    WebClientRequest request = new WebClientRequest(HttpMethod.GET, adminSDKProperties.getHost(), adminSDKProperties.getProductProfileEndpoint(), headersMap);

    return adminSDKWebClient.exchange(request, ProductProfile[].class, msg);

  }

  @Autowired
  private WebClient adminSDKWebClient;
  @Autowired
  private AdminSDKProperties adminSDKProperties;
}
