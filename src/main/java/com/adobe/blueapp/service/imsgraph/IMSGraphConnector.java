package com.adobe.blueapp.service.imsgraph;

import java.util.HashMap;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.adobe.blueapp.restclient.WebClient;
import com.adobe.blueapp.restclient.WebClientRequest;
import com.adobe.blueapp.service.imsgraph.config.IMSGraphProperties;
import com.adobe.blueapp.service.imsgraph.dto.AssignRoleToUserRequest;
import com.adobe.blueapp.service.imsgraph.dto.CreateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author siddgupt
 *
 */
@Service
public class IMSGraphConnector {

  public CreateUserDto createUser(String accessToken, String orgId, CreateUserDto createUserDto) {
    String msg = "Error while creating user";

    HashMap<String, String> headersMap = new HashMap<>();
    headersMap.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    headersMap.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
    headersMap.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

    Entity<CreateUserDto> entity = Entity.entity(createUserDto, MediaType.APPLICATION_JSON);
    String path = String.format(imsGraphProperties.getUsersEndpoint(), orgId);

    WebClientRequest request = new WebClientRequest(HttpMethod.POST, imsGraphProperties.getHost(), path, headersMap, entity);

    return imsGraphWebClient.exchange(request, CreateUserDto.class, msg);
  }

  public String assignRole(String accessToken, String orgId, String userId, String namedRoleCode, String target) {
    String msg = "Error while assigning role to user";

    HashMap<String, String> headersMap = new HashMap<>();
    headersMap.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    headersMap.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
    headersMap.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

    AssignRoleToUserRequest assignRoleToUserRequest = new AssignRoleToUserRequest();
    assignRoleToUserRequest.setNamedRoleCode(namedRoleCode);
    assignRoleToUserRequest.setTarget(target);

    Entity<AssignRoleToUserRequest> entity = Entity.entity(assignRoleToUserRequest, MediaType.APPLICATION_JSON);
    String path = String.format(imsGraphProperties.getRolesEndpoint(), orgId, userId);

    WebClientRequest request = new WebClientRequest(HttpMethod.POST, imsGraphProperties.getHost(), path, headersMap, entity);

    return imsGraphWebClient.exchange(request, String.class, msg);
  }

  @Autowired
  private WebClient imsGraphWebClient;
  @Autowired
  private IMSGraphProperties imsGraphProperties;
}