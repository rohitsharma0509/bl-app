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

package com.adobe.blueapp.resource;

import static org.assertj.core.api.Assertions.assertThat;

import com.adobe.blueapp.dto.Something;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Sample Integration Test.
 * The test sets up a SpringBoot context for end-to-end test of the application
 * and uses TestRestTemplate to invoke apis '/ping' and '/myfirstapi'.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "SSL_ENABLED=false" })
public class ApplicationIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testPing() {
    String body = this.restTemplate.getForObject("/blueapp/ping", String.class);
    assertThat(body).isEqualTo("pong");
  }

  @Test
  public void testResource() {
    Something body = this.restTemplate.exchange("/blueapp/myfirstapi", HttpMethod.GET, null, Something.class).getBody();
    assertThat(body.getMessage()).isEqualTo("At times something is Everything");
  }
}
