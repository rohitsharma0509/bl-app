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

package com.adobe.blueapp.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Custom rest exception from Rest api's
 * 
 * @author siddgupt
 *
 */
public class RestApiException extends WebApplicationException {

  private static final long serialVersionUID = 1L;
  private String code;
  private Status status;

  public RestApiException(String code, String message, Status status) {
    super(message, status);
    this.code = code;
    this.status = status;
  }

  public RestApiException(String code, String message, Status status, Throwable cause) {
    super(message, cause, status);
    this.code = code;
    this.status = status;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Response.Status getStatus() {
    return status;
  }

  public void setStatus(Response.Status status) {
    this.status = status;
  }

}
