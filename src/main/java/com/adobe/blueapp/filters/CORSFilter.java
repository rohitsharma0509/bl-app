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

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.springframework.http.HttpHeaders;

/**
 * Implementation of cross origion resource sharing rules
 * 
 * For more details: https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS
 * 
 * @author siddgupt
 *
 */
@Provider
public class CORSFilter implements ContainerResponseFilter {

  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

    String origin = requestContext.getHeaderString(HttpHeaders.ORIGIN);

    if (requestContext.getMethod().equals(HttpMethod.OPTIONS)) {
      responseContext.setEntity("");
    }

    responseContext.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, true);
    responseContext.getHeaders().add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, 1800);
    responseContext.getHeaders().add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Length, ETag, X-Server");
    responseContext.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "PUT, DELETE, POST, GET, OPTIONS");
    responseContext.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
    responseContext.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                                     "Accept, Accept-Charset, Accept-Encoding, Accept-Language, Connection, Content-Length, Content-Type, Cookie, Host, Origin, Referer, User-Agent, x-api-client-id, Authorization, x-csrftoken, X-Requested-With, X-request-id, If-None-Match, If-Match");

  }

}
