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

import javax.ws.rs.core.Response;

/**
 * Web client to execute http requests
 * 
 * @author siddgupt
 *
 */
public interface WebClient {

  /**
   * Execute the HTTP method to the given URI template, writing the given request entity to the request, and returns the response
   *
   * @param request
   *          the HTTP request object
   *          Request Entity
   * @return Http Response
   */
  Response exchange(WebClientRequest request);

  /**
   * Execute the HTTP method to the given URI template, writing the given request entity to the request, and returns the response
   * 
   * @param <T>
   *          Response Type
   * @param request
   *          the HTTP request object
   * @param type
   *          Response Type
   * @param msg
   *          Error message to throw in case of error
   * @return Http Response
   */
  <T> T exchange(WebClientRequest request, Class<T> type, String msg);

}
