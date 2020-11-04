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

package com.adobe.blueapp.service.ims.config;

/**
 * 
 * @author siddgupt
 *
 */
public class IMSClientProperties {

  private String clientId;
  private String clientSecret;
  private String clientOauthCode;

  public IMSClientProperties() {
    super();
  }

  public IMSClientProperties(String clientId, String clientSecret, String clientOauthCode) {
    super();
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.clientOauthCode = clientOauthCode;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public String getClientOauthCode() {
    return clientOauthCode;
  }

  public void setClientOauthCode(String clientOauthCode) {
    this.clientOauthCode = clientOauthCode;
  }
}