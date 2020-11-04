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

package com.adobe.blueapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * This class contains the properties related to Hystrix configuration.
 *
 * @author siddgupt
 */
@ConfigurationProperties("com.adobe.blueapp.hystrix")
public class HystrixProperties {

  private String name;

  private String group;

  private Integer executionTimeoutInMilliseconds;
  
  private Integer corePoolSize;
  
  private Boolean allowMaximumSizeToDivergeFromCoreSize;
  
  private Integer maximumPoolSize;
  
  private Boolean requestLogEnabled;
  
  private Integer circuitBreakerReqVolumeThreshold;
  
  private Integer circuitBreakerErrorThresholdPercentage;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public Integer getExecutionTimeoutInMilliseconds() {
    return executionTimeoutInMilliseconds;
  }

  public void setExecutionTimeoutInMilliseconds(Integer executionTimeoutInMilliseconds) {
    this.executionTimeoutInMilliseconds = executionTimeoutInMilliseconds;
  }

  public Integer getCorePoolSize() {
    return corePoolSize;
  }

  public void setCorePoolSize(Integer corePoolSize) {
    this.corePoolSize = corePoolSize;
  }

  public Boolean getAllowMaximumSizeToDivergeFromCoreSize() {
    return allowMaximumSizeToDivergeFromCoreSize;
  }

  public void setAllowMaximumSizeToDivergeFromCoreSize(Boolean allowMaximumSizeToDivergeFromCoreSize) {
    this.allowMaximumSizeToDivergeFromCoreSize = allowMaximumSizeToDivergeFromCoreSize;
  }

  public Integer getMaximumPoolSize() {
    return maximumPoolSize;
  }

  public void setMaximumPoolSize(Integer maximumPoolSize) {
    this.maximumPoolSize = maximumPoolSize;
  }

  public Boolean getRequestLogEnabled() {
    return requestLogEnabled;
  }

  public void setRequestLogEnabled(Boolean requestLogEnabled) {
    this.requestLogEnabled = requestLogEnabled;
  }

  public Integer getCircuitBreakerReqVolumeThreshold() {
    return circuitBreakerReqVolumeThreshold;
  }

  public void setCircuitBreakerReqVolumeThreshold(Integer circuitBreakerReqVolumeThreshold) {
    this.circuitBreakerReqVolumeThreshold = circuitBreakerReqVolumeThreshold;
  }

  public Integer getCircuitBreakerErrorThresholdPercentage() {
    return circuitBreakerErrorThresholdPercentage;
  }

  public void setCircuitBreakerErrorThresholdPercentage(Integer circuitBreakerErrorThresholdPercentage) {
    this.circuitBreakerErrorThresholdPercentage = circuitBreakerErrorThresholdPercentage;
  }
}