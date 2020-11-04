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

package com.adobe.blueapp.restclient;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import com.adobe.asr.connector.core.AsrHttpClient;
import com.adobe.blueapp.mappers.JerseyObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.internal.FormDataParamInjectionFeature;
import org.springframework.beans.factory.InitializingBean;

/**
 * WebClient for making external network calls
 * 
 * @author siddgupt
 *
 */
public class ASRHttpWebClient extends AbstractWebClient implements InitializingBean {

  public ASRHttpWebClient(String errorCode) {
    setErrorCode(errorCode);
  }

  public ASRHttpWebClient(String errorCode, Set<Integer> successStatusSet) {
    setErrorCode(errorCode);
    setSuccessStatusSet(successStatusSet);
  }

  @Override
  public Response exchange(WebClientRequest request) {

    try {
      WebTarget webTarget = client.target(request.getUrl());
      if (StringUtils.isNotEmpty(request.getPath())) {
        webTarget = webTarget.path(request.getPath());
      }
      webTarget = setQueryParameters(webTarget, request.getQueryParams());

      Invocation.Builder invocationBuilder = webTarget.request();
      invocationBuilder.headers(getHeaders(request.getHeaders()));

      long startTime = System.currentTimeMillis();
      Response response = invocationBuilder.method(request.getHttpMethod(), request.getEntity());
      long duration = System.currentTimeMillis() - startTime;

      getLogger().info("Total time taken in executing HttpMethod={} WebTarget={} Totaltime={}", request.getHttpMethod(), webTarget.getUri(), duration);
      return response;
    }
    catch (Exception e) {
      getLogger().error("Exception while calling external URL", e);
      throw e;
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    asrHttpClient = new AsrHttpClient();
    client = asrHttpClient.getHttpClient(null).register(JerseyObjectMapper.class).register(JacksonFeature.class)
        .register(MultiPartFeature.class).register(FormDataParamInjectionFeature.class);
  }

  private WebTarget setQueryParameters(WebTarget webTarget, Map<String, String> queryparams) {
    if (queryparams == null) {
      return webTarget;
    }

    for (Entry<String, String> key : queryparams.entrySet()) {
      webTarget = webTarget.queryParam(key.getKey(), key.getValue());
    }
    return webTarget;
  }

  private MultivaluedHashMap<String, Object> getHeaders(Map<String, String> headers) {
    if (headers == null) {
      return null;
    }
    MultivaluedHashMap<String, Object> headersMap = new MultivaluedHashMap<>();

    for (Entry<String, String> key : headers.entrySet()) {
      headersMap.add(key.getKey(), key.getValue());
    }

    return headersMap;
  }

  private AsrHttpClient asrHttpClient;
  private Client client;

}
