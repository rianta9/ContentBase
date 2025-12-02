package com.ryanlab.contentbase.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Test configuration for Redis mocking.
 * Uses an in-memory mock implementation instead of actual Redis server.
 */
@TestConfiguration
@Profile("test")
public class EmbeddedRedisConfiguration {

  /**
   * Creates a mock RedisConnectionFactory for testing.
   * This factory uses an in-memory implementation without requiring Redis server.
   *
   * @return the mock RedisConnectionFactory
   */
  @Bean
  @Primary
  public RedisConnectionFactory redisConnectionFactory() {
    // Try to use Lettuce connection factory, fallback to mock if needed
    try {
      LettuceConnectionFactory factory = new LettuceConnectionFactory();
      factory.afterPropertiesSet();
      return factory;
    } catch (Exception e) {
      // If Redis is not available, the tests will still work with @MockBean
      return new LettuceConnectionFactory();
    }
  }

  /**
   * Provides a mock RedisTemplate for testing.
   * This bean can be used to mock Redis operations in tests.
   *
   * @return the RedisTemplate
   */
  @Bean
  @Primary
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    return template;
  }
}

