package com.adobe.blueapp.service.sign.dto;

import java.util.List;

/**
 * 
 * @author siddgupt
 *
 */
public class UserViewResponse {

  private List<UserView> userViewList;

  public List<UserView> getUserViewList() {
    return userViewList;
  }

  public void setUserViewList(List<UserView> userViewList) {
    this.userViewList = userViewList;
  }

  public static class UserView {

    private String name;
    private String embeddedCode;
    private String expiration;
    private String url;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmbeddedCode() {
      return embeddedCode;
    }

    public void setEmbeddedCode(String embeddedCode) {
      this.embeddedCode = embeddedCode;
    }

    public String getExpiration() {
      return expiration;
    }

    public void setExpiration(String expiration) {
      this.expiration = expiration;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }
  }

}
