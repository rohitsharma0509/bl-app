package com.adobe.blueapp.resource;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlueAppIntegrationTest extends ApplicationIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testSampleBlueApp() {
    String body = this.restTemplate.getForObject("/blueapp", String.class);
    assertThat(body).isNotBlank();
  }

}