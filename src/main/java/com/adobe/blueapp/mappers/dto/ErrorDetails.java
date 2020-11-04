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

package com.adobe.blueapp.mappers.dto;

import com.adobe.blueapp.filters.CustomLoggingFilter;
import org.slf4j.MDC;

/**
 * 
 * @author siddgupt
 *
 */
public class ErrorDetails {

  private String code;
  private String message;
  private String logRef = MDC.get(CustomLoggingFilter.SESSION_ID_KEY);

  public ErrorDetails(String code, String message) {
    super();
    this.code = code;
    this.message = message;
  }

  public ErrorDetails() {
    super();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getLogRef() {
    return logRef;
  }

  public void setLogRef(String logRef) {
    this.logRef = logRef;
  }
}
