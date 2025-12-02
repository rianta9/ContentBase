package com.ryanlab.contentbase.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Mock Redis configuration for tests.
 * Provides mock beans to avoid requiring actual Redis server during testing.
 */
@TestConfiguration
@Profile("test")
public class MockRedisConfiguration {

  /**
   * Provides a mock RedisConnectionFactory that prevents connection failures.
   *
   * @return mock RedisConnectionFactory
   */
  @Bean
  @Primary
  public RedisConnectionFactory redisConnectionFactory() {
    return mock(RedisConnectionFactory.class);
  }

  /**
   * Provides a mock RedisTemplate that won't fail when Redis is unavailable.
   * Configures opsForValue() to return a mock ValueOperations with sensible defaults.
   *
   * @return mock RedisTemplate
   */
  @Bean
  @Primary
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> redisTemplate = mock(RedisTemplate.class);
    ValueOperations<String, Object> valueOps = mock(ValueOperations.class);
    
    // Mock common operations
    when(redisTemplate.opsForValue()).thenReturn(valueOps);
    when(valueOps.increment(any())).thenReturn(1L);
    when(valueOps.increment(any(), any(Long.class))).thenReturn(1L);
    when(valueOps.get(any())).thenReturn(null);
    
    return redisTemplate;
  }

  /**
   * Provides a mock StringRedisTemplate.
   *
   * @return mock StringRedisTemplate
   */
  @Bean
  @Primary
  public StringRedisTemplate stringRedisTemplate() {
    StringRedisTemplate stringRedisTemplate = mock(StringRedisTemplate.class);
    ValueOperations<String, String> valueOps = mock(ValueOperations.class);
    
    when(stringRedisTemplate.opsForValue()).thenReturn(valueOps);
    when(valueOps.get(any())).thenReturn(null);
    
    return stringRedisTemplate;
  }
}
