package com.adobe.blueapp.service.sign.dto;

/**
 * 
 * @author siddgupt
 *
 */
public class UserViewInfoRequest {
  private String name;
  private CommonViewConfiguration CommonViewConfigurationObject;

  public String getName() {
    return name;
  }

  public CommonViewConfiguration getCommonViewConfiguration() {
    return CommonViewConfigurationObject;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCommonViewConfiguration(CommonViewConfiguration commonViewConfigurationObject) {
    this.CommonViewConfigurationObject = commonViewConfigurationObject;
  }

  public static class CommonViewConfiguration {
    private boolean autoLoginUser;
    private boolean noChrome;

    public boolean getAutoLoginUser() {
      return autoLoginUser;
    }

    public boolean getNoChrome() {
      return noChrome;
    }

    public void setAutoLoginUser(boolean autoLoginUser) {
      this.autoLoginUser = autoLoginUser;
    }

    public void setNoChrome(boolean noChrome) {
      this.noChrome = noChrome;
    }
  }
}
