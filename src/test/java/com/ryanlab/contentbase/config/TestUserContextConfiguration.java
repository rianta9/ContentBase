package com.ryanlab.contentbase.config;

import java.util.Arrays;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.ryanlab.contentbase.configuration.session.UserContext;

/**
 * Test configuration for UserContext bean.
 * Provides a mock UserContext for tests since @SessionScope doesn't work in tests.
 */
@TestConfiguration
public class TestUserContextConfiguration {

  @Bean
  @Primary
  public UserContext userContext() {
    return UserContext.builder()
      .username("testuser")
      .fullName("Test User")
      .avatarUrl("https://example.com/avatar.jpg")
      .roles(Arrays.asList("ROLE_ADMIN", "ROLE_EDITOR"))
      .build();
  }
}
