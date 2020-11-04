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

import java.util.Map;

import javax.ws.rs.client.Entity;

/**
 * Request Object for ASR Web Client.
 *
 * @author siddgupt
 */
public class WebClientRequest {
  private String httpMethod;
  private String url;
  private String path;
  private Map<String, String> queryParams;
  private Map<String, String> headers;
  private Entity<?> entity;

  public WebClientRequest(String httpMethod, String url, Map<String, String> headers) {
    this(httpMethod, url, null, headers);
  }

  public WebClientRequest(String httpMethod, String url, String path, Map<String, String> headers) {
    this(httpMethod, url, path, headers, null);
  }

  public WebClientRequest(String httpMethod, String url, String path, Map<String, String> headers, Entity<?> entity) {
    this(httpMethod, url, path, null, headers, entity);
  }

  public WebClientRequest(String httpMethod, String url, String path, Map<String, String> queryParams, Map<String, String> headers, Entity<?> entity) {
    super();
    setHttpMethod(httpMethod);
    setUrl(url);
    setPath(path);
    setQueryParams(queryParams);
    setHeaders(headers);
    setEntity(entity);
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Map<String, String> getQueryParams() {
    return queryParams;
  }

  public void setQueryParams(Map<String, String> queryParams) {
    this.queryParams = queryParams;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public Entity<?> getEntity() {
    return entity;
  }

  public void setEntity(Entity<?> entity) {
    this.entity = entity;
  }

}
