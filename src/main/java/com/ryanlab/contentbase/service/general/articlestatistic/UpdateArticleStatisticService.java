package com.ryanlab.contentbase.service.general.articlestatistic;

import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ryanlab.contentbase.core.exception.ResourceNotFoundException;
import com.ryanlab.contentbase.model.entity.ArticleStatistic;
import com.ryanlab.contentbase.repository.jpa.ArticleStatisticJpaRepository;
import com.ryanlab.contentbase.service.cached.RedisService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateArticleStatisticService {
  ArticleStatisticJpaRepository articleStatisticJpaRepository;
  RedisService redisService;

  private static final String VIEW_KEY_PREFIX = "article_views:";
  private static final String LIKE_DELTA_KEY_PREFIX = "article_likes_delta:";
  private static final String LIKED_USERS_KEY_PREFIX = "article_liked_users:";

  /**
   * Increments the view count for a specific article in Redis.
   *
   * @param articleId the ID of the article whose view count is to be
   *                  incremented
   */
  @Transactional
  public void incrementViewCount(String articleId) {
    String redisKey = VIEW_KEY_PREFIX + articleId;
    redisService.increment(redisKey);
  }

  /**
   * Scheduled task that synchronizes view counts from Redis to the database
   * every 5 minutes. Uses "Write-Behind" pattern: Reads Redis -> ADDS to DB ->
   * Deletes Redis Key.
   */
  @Scheduled(fixedRate = 300000) // 5 minutes
  @Transactional
  public void syncViewCountToDatabase() {
    log.info("Starting View Count Sync Job...");
    Set<String> keys = redisService.getKeys(VIEW_KEY_PREFIX + "*");
    if (keys == null || keys.isEmpty()) {
      log.info("No view count keys to sync.");
      return;
    }

    for (String key : keys) {
      try {
        String articleId = key.replace(VIEW_KEY_PREFIX, "");
        Long viewsInRedis = redisService.get(key);

        if (viewsInRedis != null && viewsInRedis > 0) {
          ArticleStatistic articleStatistic =
            articleStatisticJpaRepository.findById(articleId).orElseGet(() -> {
              // Create new if not exists
              ArticleStatistic newStat = new ArticleStatistic();
              newStat.setArticleId(articleId);
              newStat.setNumberOfViews(0L);
              newStat.setNumberOfReviews(0L);
              newStat.setNumberOfFavourites(0L);
              newStat.setNumberOfFollows(0L);
              newStat.setAveragePoint(0.0);
              return newStat;
            });

          // CRITICAL FIX: ADD to existing count, don't replace
          Long currentDbViews = articleStatistic.getNumberOfViews() == null ? 0L
            : articleStatistic.getNumberOfViews();
          articleStatistic.setNumberOfViews(currentDbViews + viewsInRedis);
          articleStatisticJpaRepository.save(articleStatistic);

          // Delete key after successful sync
          redisService.delete(key);
          log.debug("Synced {} views for article {}", viewsInRedis, articleId);
        }
      } catch (Exception e) {
        log.error("Error syncing view count for key: {}", key, e);
      }
    }
    log.info("View Count Sync Job Completed.");
  }

  /**
   * Increments the comment count for a specific article. Direct DB update for
   * now (Phase 3 will move this to Mongo).
   *
   * @param articleId the ID of the article
   */
  @Transactional
  public void incrementCommentCount(String articleId) {
    ArticleStatistic articleStatistic =
      articleStatisticJpaRepository.findById(articleId)
                                   .orElseThrow(
                                     () -> new ResourceNotFoundException(
                                       String.format(
                                         "Article Statistic Not Found: %s",
                                         articleId),
                                       new Object[] { "Article Statistic" },
                                       new Object[] { String.format(
                                         "ArticleId = %s",
                                         articleId) }));

    Long currentCount = articleStatistic.getNumberOfReviews() != null
      ? articleStatistic.getNumberOfReviews() : 0L;
    articleStatistic.setNumberOfReviews(currentCount + 1);
    articleStatisticJpaRepository.save(articleStatistic);
  }

  /**
   * Toggles the like status for a user on an article using Redis. Updates the
   * Set of liked users and the Delta counter. NO direct DB writes.
   *
   * @param articleId the ID of the article
   * @param username  the username
   * @return true if liked, false if unliked
   */
  public boolean toggleLike(String articleId, String username) {
    String setKey = LIKED_USERS_KEY_PREFIX + articleId;
    String deltaKey = LIKE_DELTA_KEY_PREFIX + articleId;

    if (redisService.isSetMember(setKey, username)) {
      // User already liked -> Unlike
      redisService.removeFromSet(setKey, username);
      redisService.decrement(deltaKey);
      log.debug("User {} unliked article {}", username, articleId);
      return false;
    } else {
      // User hasn't liked -> Like
      redisService.addToSet(setKey, username);
      redisService.increment(deltaKey);
      log.debug("User {} liked article {}", username, articleId);
      return true;
    }
  }

  /**
   * Scheduled task that synchronizes like counts from Redis to the database.
   * Flushes the delta (changes) to the DB.
   */
  @Scheduled(fixedRate = 300000) // 5 minutes
  @Transactional
  public void syncLikeCountToDatabase() {
    log.info("Starting Like Count Sync Job...");
    Set<String> keys = redisService.getKeys(LIKE_DELTA_KEY_PREFIX + "*");
    if (keys == null || keys.isEmpty()) {
      log.info("No like count deltas to sync.");
      return;
    }

    for (String key : keys) {
      try {
        String articleId = key.replace(LIKE_DELTA_KEY_PREFIX, "");
        Long deltaLikes = redisService.get(key); // Can be positive or negative

        if (deltaLikes != null && deltaLikes != 0) {
          ArticleStatistic articleStatistic =
            articleStatisticJpaRepository.findById(articleId)
                                         .orElseThrow(
                                           () -> new RuntimeException(
                                             "Article Statistic not found for ID: "
                                               + articleId));

          Long currentLikes = articleStatistic.getNumberOfFavourites() == null
            ? 0L : articleStatistic.getNumberOfFavourites();
          long newLikes = currentLikes + deltaLikes;

          // Prevent negative likes
          if (newLikes < 0)
            newLikes = 0;

          articleStatistic.setNumberOfFavourites(newLikes);
          articleStatisticJpaRepository.save(articleStatistic);

          // Delete delta key after sync
          redisService.delete(key);
          log.debug(
            "Synced {} like delta for article {}",
            deltaLikes,
            articleId);
        }
      } catch (Exception e) {
        log.error("Error syncing like count for key: {}", key, e);
      }
    }
    log.info("Like Count Sync Job Completed.");
  }

  /**
   * Gets the like count for an article. Returns DB count + Redis Delta (for
   * near real-time accuracy).
   *
   * @param articleId the ID of the article
   * @return the like count
   */
  public Long getLikeCount(String articleId) {
    // Get DB count
    Long dbCount =
      articleStatisticJpaRepository.findById(articleId)
                                   .map(ArticleStatistic::getNumberOfFavourites)
                                   .orElse(0L);

    // Get Redis Delta
    String deltaKey = LIKE_DELTA_KEY_PREFIX + articleId;
    Long delta = redisService.get(deltaKey);
    if (delta == null)
      delta = 0L;

    long total = dbCount + delta;
    return total < 0 ? 0L : total;
  }
}
