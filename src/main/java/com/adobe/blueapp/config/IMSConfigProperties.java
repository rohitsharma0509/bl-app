package com.adobe.blueapp.config;

import com.adobe.blueapp.service.ims.config.IMSClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author siddgupt
 *
 */
@ConfigurationProperties("com.adobe.blueapp")
public class IMSConfigProperties {

  private IMSClientProperties serviceClient;

  public IMSClientProperties getServiceClient() {
    return serviceClient;
  }

  public void setServiceClient(IMSClientProperties serviceClient) {
    this.serviceClient = serviceClient;
  }
}
