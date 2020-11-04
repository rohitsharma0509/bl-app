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

package com.adobe.blueapp.filters;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.adobe.asr.autoconfigure.apigateway.dto.AdobeIOAuthInfo;
import org.slf4j.MDC;

/**
 * This class adds the common values that needs to be logged.
 * 
 * @author siddgupt
 *
 */
public class CustomLoggingFilter implements Filter {

  public static final String SESSION_ID_KEY = "sessionId";
  private static final String REQUEST_PATH_KEY = "requestPath";
  private static final String REQUEST_METHOD_KEY = "requestMethod";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    MDC.put(REQUEST_METHOD_KEY, httpRequest.getMethod());
    MDC.put(REQUEST_PATH_KEY, httpRequest.getPathInfo());
    MDC.put(SESSION_ID_KEY, UUID.randomUUID().toString());
    MDC.put(AdobeIOAuthInfo.HEADER_NAME_USER_ID, httpRequest.getHeader(AdobeIOAuthInfo.HEADER_NAME_USER_ID));
    MDC.put(AdobeIOAuthInfo.HEADER_NAME_CLIENT_ID, httpRequest.getHeader(AdobeIOAuthInfo.HEADER_NAME_CLIENT_ID));

    chain.doFilter(request, response);

    MDC.remove(REQUEST_METHOD_KEY);
    MDC.remove(REQUEST_PATH_KEY);
    MDC.remove(SESSION_ID_KEY);
    MDC.remove(AdobeIOAuthInfo.HEADER_NAME_USER_ID);
    MDC.remove(AdobeIOAuthInfo.HEADER_NAME_CLIENT_ID);
  }
}
