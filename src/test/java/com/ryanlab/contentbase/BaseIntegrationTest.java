package com.ryanlab.contentbase;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.ryanlab.contentbase.config.MockRedisConfiguration;
import com.ryanlab.contentbase.config.TestUserContextConfiguration;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import({TestUserContextConfiguration.class, MockRedisConfiguration.class})
public abstract class BaseIntegrationTest {
  // Base class for all integration tests
}
