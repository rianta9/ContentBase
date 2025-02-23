package com.ryanlab.contentbase.service.general.contentstatistic;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ryanlab.contentbase.core.exception.ResourceNotFoundException;
import com.ryanlab.contentbase.model.entity.ContentStatistic;
import com.ryanlab.contentbase.repository.jpa.ContentStatisticJpaRepository;
import com.ryanlab.contentbase.service.cached.RedisService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateContentStatisticService {
  final ContentStatisticJpaRepository contentStatisticJpaRepository;
  final RedisService redisService;

  /**
   * Increments the view count for a specific content in Redis.
   *
   * @param contentId the ID of the content whose view count is to be
   *                  incremented
   */
  @Transactional
  public void incrementViewCount(String contentId) {
    String redisKey = "content_views:" + contentId;
    redisService.increment(redisKey);
  }

  /**
   * Scheduled task that synchronizes view counts from Redis to the database
   * every 5 minutes. For each content statistic in the database, it fetches the
   * view count from Redis using a specific key pattern. If the view count
   * exists in Redis, it updates the database with the retrieved value and
   * deletes the key from Redis to prevent double processing.
   */
  @Scheduled(fixedRate = 300000)
  private void syncViewCountToDatabase() {
    contentStatisticJpaRepository.findAll().forEach(item -> {
      String key = "content_views:" + item.id();
      Long views = redisService.get(key);
      if (views != null) {
        item.viewCount(views);
        contentStatisticJpaRepository.save(item);
        redisService.delete(key);
      }
    });
  }

  /**
   * Synchronizes the view count for a specific content ID from Redis to the
   * database.
   *
   * @param contentId the ID of the content whose view count is to be
   *                  synchronized
   * @param redisKey  the Redis key corresponding to the content's view count
   * @throws ResourceNotFoundException if the content statistic is not found in
   *                                   the database
   */
  public void syncViewCountToDatabase(String contentId, String redisKey) {
    Long viewCount = redisService.get(redisKey);
    if (viewCount == null) {
      viewCount = 0L;
    }

    ContentStatistic contentStatistic =
      contentStatisticJpaRepository.findOneByContentId(contentId)
                                   .orElseThrow(
                                     () -> new ResourceNotFoundException(
                                       String.format(
                                         "Content Statistic Not Found: %s",
                                         contentId),
                                       new Object[] { "Content Statistic" },
                                       new Object[] { String.format(
                                         "ContentId = %s",
                                         contentId) }));
    contentStatistic.viewCount(viewCount);
    contentStatisticJpaRepository.save(contentStatistic);
    redisService.delete(redisKey);
  }
}
