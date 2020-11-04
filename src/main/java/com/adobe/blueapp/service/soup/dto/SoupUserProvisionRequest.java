package com.adobe.blueapp.service.soup.dto;

/**
 * 
 * @author siddgupt
 *
 */
public class SoupUserProvisionRequest {

  private String delegate_guid;
  private String auth_src;
  private String product_profile_id;

  public SoupUserProvisionRequest() {
    super();
    // TODO Auto-generated constructor stub
  }

  public SoupUserProvisionRequest(String delegate_guid, String auth_src, String product_profile_id) {
    super();
    this.delegate_guid = delegate_guid;
    this.auth_src = auth_src;
    this.product_profile_id = product_profile_id;
  }

  public String getDelegate_guid() {
    return delegate_guid;
  }

  public void setDelegate_guid(String delegate_guid) {
    this.delegate_guid = delegate_guid;
  }

  public String getAuth_src() {
    return auth_src;
  }

  public void setAuth_src(String auth_src) {
    this.auth_src = auth_src;
  }

  public String getProduct_profile_id() {
    return product_profile_id;
  }

  public void setProduct_profile_id(String product_profile_id) {
    this.product_profile_id = product_profile_id;
  }
}
