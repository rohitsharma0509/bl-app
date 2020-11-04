package com.adobe.blueapp.service.sign.dto;

import java.util.List;

/**
 * 
 * @author siddgupt
 *
 */
public class AgreementViewResponse {

  private List<AgreementView> agreementViewList;

  public List<AgreementView> getAgreementViewList() {
    return agreementViewList;
  }

  public void setAgreementViewList(List<AgreementView> agreementViewList) {
    this.agreementViewList = agreementViewList;
  }


  public static class AgreementView {

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

