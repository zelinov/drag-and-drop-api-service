package ru.izelinov.draganddrop.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class Configuration {

  @Bean
  protected TestRestTemplate testRestTemplate() {
    return new TestRestTemplate();
  }
}
