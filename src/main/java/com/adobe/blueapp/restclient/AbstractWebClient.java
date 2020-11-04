package com.adobe.blueapp.restclient;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.adobe.blueapp.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author siddgupt
 *
 */
public abstract class AbstractWebClient implements WebClient {

  @Override
  public <T> T exchange(WebClientRequest request, Class<T> type, String msg) {
    try {
      Response response = exchange(request);

      if (successStatusSet.contains(response.getStatus())) {
        return response.readEntity(type);
      }
      else if (response.getStatus() == Status.NO_CONTENT.getStatusCode()) {
        return null;
      }
      else {
        getLogger().error("{} status={} responsebody={}", msg, response.getStatus(), response.readEntity(String.class));
        throwNewIMSException(msg, response.getStatus(), null);
      }
    }
    catch (ServiceException e) {
      throw e;
    }
    catch (Exception e) {
      getLogger().error(msg, e);
      throwNewIMSException(msg, Status.INTERNAL_SERVER_ERROR, e);
    }

    return null;

  }

  private void throwNewIMSException(String message, Status status, Exception e) throws ServiceException {
    throwNewIMSException(message, status.getStatusCode(), e);
  }

  private void throwNewIMSException(String message, int statuscode, Exception e) throws ServiceException {
    throw new ServiceException(message, e).withCode(getErrorCode()).withStatus(statuscode);
  }
  
  public Logger getLogger() {
    return LoggerFactory.getLogger(this.getClass());
  }

  private String errorCode;
  private Set<Integer> successStatusSet = new HashSet<>(Arrays.asList(Status.OK.getStatusCode(), Status.CREATED.getStatusCode()));

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public Set<Integer> getSuccessStatusSet() {
    return successStatusSet;
  }

  public void setSuccessStatusSet(Set<Integer> successStatusSet) {
    this.successStatusSet = successStatusSet;
  }

}
