/*
 * ************************************************************************
 *  ADOBE CONFIDENTIAL
 *  ___________________
 *
 *  Copyright 2019 Adobe
 *  All Rights Reserved.
 *
 *  NOTICE: All information contained herein is, and remains
 *  the property of Adobe and its suppliers, if any. The intellectual
 *  and technical concepts contained herein are proprietary to Adobe
 *  and its suppliers and are protected by all applicable intellectual
 *  property laws, including trade secret and copyright laws.
 *  Dissemination of this information or reproduction of this material
 *  is strictly forbidden unless prior written permission is obtained
 *  from Adobe.
 * ************************************************************************
 */

package com.adobe.blueapp.exception;

import javax.ws.rs.core.Response.Status;

/**
 * Custom exception thrown by Service layer.
 *
 * @author siddgupt
 */
public class ServiceException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private String code;
  private int status;

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public String getCode() {
    return code;
  }

  public ServiceException withCode(String code) {
    this.code = code;
    return this;
  }

  public int getStatus() {
    return status;
  }

  public ServiceException withStatus(int status) {
    this.status = status;
    return this;
  }

  public ServiceException withStatus(Status status) {
    if (status != null) {
      this.status = status.getStatusCode();
    }
    return this;
  }
}
