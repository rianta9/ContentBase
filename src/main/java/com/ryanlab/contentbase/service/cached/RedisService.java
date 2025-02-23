package com.ryanlab.contentbase.service.cached;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

  private final RedisTemplate<String, Object> redisTemplate;

  @Autowired
  public RedisService(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  /**
   * Sets a value in Redis with a specified timeout in seconds.
   * 
   * @param key          the key to set
   * @param value        the value to set
   * @param timeoutSeconds the timeout in seconds
   */
  public void set(String key, Object value, long timeoutSeconds) {
    redisTemplate.opsForValue()
                 .set(key, value, timeoutSeconds, TimeUnit.SECONDS);
  }

  /**
   * Retrieves a value from Redis.
   * 
   * @param key the key to retrieve
   * @return the retrieved value
   */
  @SuppressWarnings("unchecked")
  public <T> T get(String key) {
    return (T) redisTemplate.opsForValue().get(key);
  }

  /**
   * Deletes a key from Redis.
   * 
   * @param key the key to delete
   */
  public void delete(String key) {
    redisTemplate.delete(key);
  }

/**
 * Increments the value stored at the specified key in Redis by one.
 *
 * @param key the key whose value is to be incremented
 * @return the new value after the increment, or null if the key does not exist
 */
  public Long increment(String key) {
    return redisTemplate.opsForValue().increment(key);
  }

/**
 * Adds a value to the end of a list stored at the specified key in Redis.
 * If the key does not exist, a new list is created with the specified value.
 *
 * @param key   the key of the list to which the value is to be added
 * @param value the value to add to the list
 */
  public void addToList(String key, Object value) {
    redisTemplate.opsForList().rightPush(key, value);
  }

/**
 * Retrieves a sublist of elements from a list stored at the specified key in Redis.
 * 
 * @param key   the key of the list to retrieve
 * @param start the starting index of the sublist (inclusive)
 * @param end   the ending index of the sublist (exclusive)
 * @return the list of elements within the specified range, or an empty list if the key does not exist
 */
  @SuppressWarnings("unchecked")
  public <T> List<T> getList(String key, long start, long end) {
    return (List<T>) redisTemplate.opsForList().range(key, start, end);
  }

  /**
   * Checks if a key exists in Redis.
   * 
   * @param key the key to check
   * @return true if the key exists, false otherwise
   */
  public boolean hasKey(String key) {
    return Boolean.TRUE.equals(redisTemplate.hasKey(key));
  }
}