package com.adobe.blueapp.service.soup.dto;

/**
 * 
 * @author siddgupt
 *
 */
public class SoupUserProvisionResponse {

  private String userId;
  private String code;
  private String description;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
