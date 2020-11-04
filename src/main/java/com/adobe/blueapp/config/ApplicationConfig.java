/*************************************************************************
 * ADOBE CONFIDENTIAL ___________________
 * <p/>
 * Copyright 2020 Adobe Systems Incorporated All Rights Reserved.
 * <p/>
 * NOTICE: All information contained herein is, and remains the property of Adobe Systems
 * Incorporated and its suppliers, if any. The intellectual and technical concepts contained herein
 * are proprietary to Adobe Systems Incorporated and its suppliers and are protected by all
 * applicable intellectual property laws, including trade secret and copyright laws. Dissemination
 * of this information or reproduction of this material is strictly forbidden unless prior written
 * permission is obtained from Adobe Systems Incorporated.
 **************************************************************************/

package com.adobe.blueapp.config;

import static javax.servlet.DispatcherType.ASYNC;
import static javax.servlet.DispatcherType.ERROR;
import static javax.servlet.DispatcherType.REQUEST;

import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.ws.rs.core.Response.Status;

import com.adobe.asr.filter.AsrFilterOrder;
import com.adobe.asr.resiliency.properties.AsrCommandOptions;
import com.adobe.blueapp.constants.ErrorCodes;
import com.adobe.blueapp.filters.CustomLoggingFilter;
import com.adobe.blueapp.restclient.ASRHttpWebClient;
import com.adobe.blueapp.restclient.AsrResilientWebClient;
import com.adobe.blueapp.restclient.WebClient;
import com.adobe.blueapp.service.adminsdk.config.AdminSDKProperties;
import com.adobe.blueapp.service.ims.CustomIMSConnector;
import com.adobe.blueapp.service.ims.config.IMSProperties;
import com.adobe.blueapp.service.imsgraph.config.IMSGraphProperties;
import com.adobe.blueapp.service.jil.config.JilProperties;
import com.adobe.blueapp.service.sign.config.AdobeSignProperties;
import com.adobe.blueapp.service.soup.config.SoupProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author siddgupt
 *
 */
@Configuration
@EnableConfigurationProperties({ HystrixProperties.class,
                                 AdobeSignProperties.class,
                                 JilProperties.class,
                                 SoupProperties.class,
                                 AdminSDKProperties.class,
                                 IMSProperties.class,
                                 IMSConfigProperties.class,
                                 IMSGraphProperties.class,
                                 MyFirstApiResourceProperties.class })
public class ApplicationConfig {

  @Autowired
  private IMSConfigProperties imsConfigProperties;

  @Autowired
  private HystrixProperties hystrixProperties;

  @Autowired
  private IMSProperties imsProperties;

  @Bean
  public CustomIMSConnector customIMSConnector() {
    return new CustomIMSConnector(imsProperties, imsConfigProperties.getServiceClient());
  }

  @Bean
  public WebClient imsWebClient() {
    return new ASRHttpWebClient(ErrorCodes.IMS_ERROR);
  }

  @Bean
  public WebClient imsGraphWebClient() {
    return new ASRHttpWebClient(ErrorCodes.IMSGRAPH_ERROR, new HashSet<>(Arrays.asList(Status.OK.getStatusCode(), Status.CREATED.getStatusCode(), Status.CONFLICT.getStatusCode())));
  }

  @Bean
  public WebClient adminSDKWebClient() {
    return new ASRHttpWebClient(ErrorCodes.ADMINSDK_ERROR);
  }

  @Bean
  public WebClient soupWebClient() {
    return new ASRHttpWebClient(ErrorCodes.SOUP_ERROR);
  }

  @Bean
  public WebClient jilWebClient() {
    return new ASRHttpWebClient(ErrorCodes.JIL_ERROR);
  }

  @Bean
  public WebClient signWebClient() {
    return new ASRHttpWebClient(ErrorCodes.SIGN_ERROR);
  }

  @Bean
  public FilterRegistrationBean<Filter> loggingFilter() {
    final FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>(new CustomLoggingFilter());
    filterRegistrationBean.setName("CustomLoggingFilter");
    filterRegistrationBean.setDispatcherTypes(REQUEST, ASYNC, ERROR);
    filterRegistrationBean.setOrder(AsrFilterOrder.REQUESTID.getOrder());
    return filterRegistrationBean;
  }

  @Bean
  public RestTemplate restTemplate() {

    var factory = new HttpComponentsClientHttpRequestFactory();
    factory.setConnectTimeout(3000);
    factory.setReadTimeout(3000);

    return new RestTemplate(factory);
  }

  @Bean
  public WebClient hystrixWebClient() {
    return new AsrResilientWebClient(getAstAsrCommandOptions(hystrixProperties));
  }

  private AsrCommandOptions getAstAsrCommandOptions(HystrixProperties hystrixProperties) {
    return new AsrCommandOptions(hystrixProperties.getName(), hystrixProperties.getGroup())
        .setAllowMaximumSizeToDivergeFromCoreSize(hystrixProperties.getAllowMaximumSizeToDivergeFromCoreSize())
        .setExecutionTimeoutInMilliseconds(hystrixProperties.getExecutionTimeoutInMilliseconds())
        .setCorePoolSize(hystrixProperties.getCorePoolSize())
        .setMaximumPoolSize(hystrixProperties.getMaximumPoolSize()).setRequestLogEnabled(hystrixProperties.getRequestLogEnabled())
        .setCircuitBreakerErrorThresholdPercentage(hystrixProperties.getCircuitBreakerErrorThresholdPercentage())
        .setCircuitBreakerRequestVolumeThreshold(hystrixProperties.getCircuitBreakerReqVolumeThreshold());
  }

}