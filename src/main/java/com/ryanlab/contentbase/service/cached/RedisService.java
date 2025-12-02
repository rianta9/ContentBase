package com.ryanlab.contentbase.service.cached;

import java.util.List;
import java.util.Set;
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
   * @param key            the key to set
   * @param value          the value to set
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
   * @return the new value after the increment, or null if the key does not
   *         exist
   */
  public Long increment(String key) {
    return redisTemplate.opsForValue().increment(key);
  }

  /**
   * Decrements the value stored at the specified key in Redis by one.
   *
   * @param key the key whose value is to be decremented
   * @return the new value after the decrement
   */
  public Long decrement(String key) {
    return redisTemplate.opsForValue().decrement(key);
  }

  /**
   * Adds a value to the end of a list stored at the specified key in Redis. If
   * the key does not exist, a new list is created with the specified value.
   *
   * @param key   the key of the list to which the value is to be added
   * @param value the value to add to the list
   */
  public void addToList(String key, Object value) {
    redisTemplate.opsForList().rightPush(key, value);
  }

  /**
   * Retrieves a sublist of elements from a list stored at the specified key in
   * Redis.
   * 
   * @param key   the key of the list to retrieve
   * @param start the starting index of the sublist (inclusive)
   * @param end   the ending index of the sublist (exclusive)
   * @return the list of elements within the specified range, or an empty list
   *         if the key does not exist
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

  /**
   * Adds a value to a set stored at the specified key in Redis.
   *
   * @param key   the key of the set
   * @param value the value to add
   * @return the number of elements added to the set
   */
  public Long addToSet(String key, Object value) {
    return redisTemplate.opsForSet().add(key, value);
  }

  /**
   * Removes a value from a set stored at the specified key in Redis.
   *
   * @param key   the key of the set
   * @param value the value to remove
   * @return the number of elements removed from the set
   */
  public Long removeFromSet(String key, Object value) {
    return redisTemplate.opsForSet().remove(key, value);
  }

  /**
   * Checks if a value is a member of a set stored at the specified key in
   * Redis.
   *
   * @param key   the key of the set
   * @param value the value to check
   * @return true if the value is a member, false otherwise
   */
  public boolean isSetMember(String key, Object value) {
    return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
  }

  /**
   * Finds all keys matching the given pattern. Note: This uses the KEYS command
   * which can be blocking. Use with caution in production.
   *
   * @param pattern the pattern to match
   * @return a set of keys matching the pattern
   */
  public Set<String> getKeys(String pattern) {
    return redisTemplate.keys(pattern);
  }
}