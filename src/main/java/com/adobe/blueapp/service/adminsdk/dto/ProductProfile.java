package com.adobe.blueapp.service.adminsdk.dto;

/**
 * 
 * @author siddgupt
 *
 */
public class ProductProfile {

  private String profile_id;
  private String org_id;
  private String name;
  private String product_name;
  private Data data;


  public String getProfile_id() {
    return profile_id;
  }

  public String getOrg_id() {
    return org_id;
  }

  public String getName() {
    return name;
  }

  public String getProduct_name() {
    return product_name;
  }

  public Data getData() {
    return data;
  }


  public void setProfile_id(String profile_id) {
    this.profile_id = profile_id;
  }

  public void setOrg_id(String org_id) {
    this.org_id = org_id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setProduct_name(String product_name) {
    this.product_name = product_name;
  }

  public void setData(Data data) {
    this.data = data;
  }

  public class Data {
    private String msft_integration_readiness;
    private String service_level;


    public String getMsft_integration_readiness() {
      return msft_integration_readiness;
    }

    public String getService_level() {
      return service_level;
    }


    public void setMsft_integration_readiness(String msft_integration_readiness) {
      this.msft_integration_readiness = msft_integration_readiness;
    }

    public void setService_level(String service_level) {
      this.service_level = service_level;
    }
  }

}

