package com.adobe.blueapp.service.jil;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.adobe.asr.connector.jil.JILConnector;
import com.adobe.asr.connector.jil.dto.Organization;
import com.adobe.asr.connector.jil.exceptions.JILConnectorException;
import com.adobe.blueapp.constants.CustomHttpHeaders;
import com.adobe.blueapp.constants.ErrorCodes;
import com.adobe.blueapp.exception.ServiceException;
import com.adobe.blueapp.restclient.WebClient;
import com.adobe.blueapp.restclient.WebClientRequest;
import com.adobe.blueapp.service.jil.config.JilProperties;
import com.adobe.blueapp.service.jil.dto.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author siddgupt
 *
 */
@Service
public class CustomJilConnector {

  public List<Organization> findOrganizationsOwnedByUser(String imsUserAccessToken) {

    try {
      return jilConnector.findOrganizationsOwnedByUser(imsUserAccessToken);
    }
    catch (JILConnectorException e) {
      throwNewJILException("Exception while generating service token", e.getHttpStatusCode(), e);
    }
    catch (Exception e) {
      throwNewJILException("Exception while generating service token", Status.INTERNAL_SERVER_ERROR, e);
    }

    return null;
  }

  public Directory getorCreateFirstT2eDirectory(String accessToken, String orgId) {
    Directory[] directories = getDirectories(accessToken, orgId);

    for (Directory directory : directories) {
      if (directory.getType().equals("TYPE2E")) {
        return directory;
      }
    }
    Directory directory = new Directory();
    directory.setType("TYPE2E");

    directory = createDirectory(accessToken, orgId, directory);
    directories = getDirectories(accessToken, orgId);

    for (Directory directory1 : directories) {
      if (directory1.getType().equals("TYPE2E")) {
        return directory1;
      }
    }

    throw new ServiceException("Error while creating/fetching directory").withCode(ErrorCodes.JIL_ERROR).withStatus(Status.INTERNAL_SERVER_ERROR);
  }

  public Directory getFirstT2eDirectory(String accessToken, String orgId) {
    Directory[] directories = getDirectories(accessToken, orgId);

    for (Directory directory : directories) {
      if (directory.getType().equals("TYPE2E")) {
        return directory;
      }
    }
    return null;
  }

  public Directory[] getDirectories(String accessToken, String orgId) {
    String msg = "Error while fetching directories";

    HashMap<String, String> headersMap = new HashMap<>();
    headersMap.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    headersMap.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
    headersMap.put(CustomHttpHeaders.X_API_KEY, jilProperties.getImsClientId());

    String path = String.format(jilProperties.getDirectoryEndpoint(), orgId);

    WebClientRequest request = new WebClientRequest(HttpMethod.GET, jilProperties.getHost(), path, headersMap);

    return jilWebClient.exchange(request, Directory[].class, msg);
  }

  public Directory createDirectory(String accessToken, String orgId, Directory directory) {
    String msg = "Error while creating directory";

    HashMap<String, String> headersMap = new HashMap<>();
    headersMap.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    headersMap.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
    headersMap.put(CustomHttpHeaders.X_API_KEY, jilProperties.getImsClientId());

    String path = String.format(jilProperties.getDirectoryEndpoint(), orgId);

    Entity<Directory> entity = Entity.entity(directory, MediaType.APPLICATION_JSON);

    WebClientRequest request = new WebClientRequest(HttpMethod.POST, jilProperties.getHost(), path, headersMap, entity);

    return jilWebClient.exchange(request, Directory.class, msg);
  }

  private void throwNewJILException(String message, Status status, Exception e) throws ServiceException {
    throwNewJILException(message, status.getStatusCode(), e);
  }

  private void throwNewJILException(String message, int statuscode, Exception e) throws ServiceException {
    throw new ServiceException(message, e).withCode(ErrorCodes.JIL_ERROR).withStatus(statuscode);
  }

  @Autowired
  private WebClient jilWebClient;
  @Autowired
  private JilProperties jilProperties;
  @Autowired
  private JILConnector jilConnector;

}
