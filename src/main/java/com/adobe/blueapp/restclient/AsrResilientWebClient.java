
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

package com.adobe.blueapp.restclient;

import static javax.ws.rs.HttpMethod.DELETE;
import static javax.ws.rs.HttpMethod.HEAD;
import static javax.ws.rs.HttpMethod.PATCH;
import static javax.ws.rs.HttpMethod.POST;
import static javax.ws.rs.HttpMethod.PUT;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.adobe.asr.connector.core.AsrHttpClient;
import com.adobe.asr.connector.core.AsrHttpRequest;
import com.adobe.asr.connector.core.AsrResilientHttpRequester;
import com.adobe.asr.resiliency.properties.AsrCommandOptions;
import com.adobe.blueapp.mappers.JerseyObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 * Resilient WebClient for ASR.
 *
 * @author siddgupt
 */
public class AsrResilientWebClient extends AbstractWebClient {

  public AsrResilientWebClient(AsrCommandOptions asrCommandOptions) {
    this.asrResilientHttpRequester = new AsrResilientHttpRequester(asrCommandOptions);
    this.asrHttpClient = new AsrHttpClient().getHttpClient(null).register(JerseyObjectMapper.class).register(JacksonFeature.class).register(MultiPartFeature.class);
  }

  @Override
  public Response exchange(WebClientRequest request) {
    return exchange(request.getHttpMethod(), request.getUrl(), request.getPath(), request.getQueryParams(), request.getHeaders(), request.getEntity());
  }

  private Response exchange(String httpMethod, String url, String path, Map<String, String> queryParams, Map<String, String> headers, Entity<?> entity) {
    try {
      WebTarget webTarget = asrHttpClient.target(url);
      if (!StringUtils.isEmpty(path)) {
        webTarget = webTarget.path(path);
      }
      webTarget = setQueryParamToWebTarget(webTarget, queryParams);

      long startTime = System.currentTimeMillis();
      Response response = null;

      switch (httpMethod) {
      case PUT:
        response = asrResilientHttpRequester.put(createAsrHttpRequest(webTarget, headers, entity));
        break;
      case POST:
        response = asrResilientHttpRequester.post(createAsrHttpRequest(webTarget, headers, entity));
        break;
      case DELETE:
        response = asrResilientHttpRequester.delete(createAsrHttpRequest(webTarget, headers, entity));
        break;
      case PATCH:
        response = asrResilientHttpRequester.patch(createAsrHttpRequest(webTarget, headers, entity));
        break;
      case HEAD:
        response = asrResilientHttpRequester.head(createAsrHttpRequest(webTarget, headers, entity));
        break;
      default:
        response = asrResilientHttpRequester.get(createAsrHttpRequest(webTarget, headers, entity));
      }

      long duration = System.currentTimeMillis() - startTime;

      getLogger().info("Total time taken in executing HttpMethod={} WebTarget={} Totaltime={}", httpMethod, webTarget.getUri(), duration);
      return response;
    }
    catch (Exception e) {
      getLogger().error("Exception while calling external URL", e);
      throw e;
    }
  }

  private AsrHttpRequest createAsrHttpRequest(WebTarget target, Map<String, String> headers, Entity<?> entity) {
    AsrHttpRequest asrHttpRequest = new AsrHttpRequest(target);
    if (null != headers && !headers.isEmpty()) {
      asrHttpRequest.setHeaders(headers);
    }
    if (null != entity)
      asrHttpRequest.setEntity(entity);

    return asrHttpRequest;
  }

  private WebTarget setQueryParamToWebTarget(WebTarget target, Map<String, String> queryParams) {
    if (null != queryParams && !queryParams.isEmpty()) {
      for (Map.Entry<String, String> entry : queryParams.entrySet()) {
        target = target.queryParam(entry.getKey(), entry.getValue());
      }
    }
    return target;
  }

  private AsrResilientHttpRequester asrResilientHttpRequester;
  private Client asrHttpClient;

}
