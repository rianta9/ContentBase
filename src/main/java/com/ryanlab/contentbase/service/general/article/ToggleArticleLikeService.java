package com.ryanlab.contentbase.service.general.article;

import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.core.exception.ResourceNotFoundException;
import com.ryanlab.contentbase.model.dto.response.article.ArticleLikeResponse;
import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;
import com.ryanlab.contentbase.service.general.articlestatistic.UpdateArticleStatisticService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ToggleArticleLikeService {
  final ArticleJpaRepository articleJpaRepository;
  final UpdateArticleStatisticService updateArticleStatisticService;

  /**
   * Toggles the like status for an article.
   *
   * @param articleId the article ID
   * @param username the username
   * @return the like response with updated status and count
   * @throws ResourceNotFoundException if the article is not found
   */
  public ArticleLikeResponse toggleLike(String articleId, String username) {
    // Verify article exists
    articleJpaRepository.findById(articleId)
                        .orElseThrow(
                          () -> new ResourceNotFoundException(
                            String.format("Article Not Found: %s", articleId),
                            new Object[] { "Article" },
                            new Object[] {
                              String.format("ArticleId = %s", articleId) }));

    boolean isLiked = updateArticleStatisticService.toggleLike(articleId, username);
    Long likeCount = updateArticleStatisticService.getLikeCount(articleId);

    return ArticleLikeResponse.builder()
                              .articleId(articleId)
                              .isLiked(isLiked)
                              .likeCount(likeCount)
                              .build();
  }
}
