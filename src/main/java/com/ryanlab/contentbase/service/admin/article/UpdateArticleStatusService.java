package com.ryanlab.contentbase.service.admin.article;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.core.exception.ResourceNotFoundException;
import com.ryanlab.contentbase.model.dto.request.article.UpdateArticleStatusRequest;
import com.ryanlab.contentbase.model.dto.response.content.ArticleResponse;
import com.ryanlab.contentbase.model.entity.Article;
import com.ryanlab.contentbase.model.mapper.ArticleMapper;
import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UpdateArticleStatusService {
  final ArticleJpaRepository articleJpaRepository;
  final ArticleMapper articleMapper;

  /**
   * Updates the status of an article.
   *
   * @param articleId the article ID
   * @param request the update status request
   * @param updatedBy the username who is updating
   * @return the updated article response
   * @throws ResourceNotFoundException if the article is not found
   * @throws IllegalArgumentException if the status is invalid
   */
  public ArticleResponse updateArticleStatus(
    String articleId, UpdateArticleStatusRequest request, String updatedBy) {
    Article article =
      articleJpaRepository.findById(articleId)
                          .orElseThrow(
                            () -> new ResourceNotFoundException(
                              String.format("Article Not Found: %s", articleId),
                              new Object[] { "Article" },
                              new Object[] {
                                String.format("ArticleId = %s", articleId) }));

    // Validate status
    String status = request.getStatus().toUpperCase();
    if (!isValidStatus(status)) {
      throw new IllegalArgumentException(
        String.format("Invalid article status: %s", status));
    }

    // If publishing, set publishedAt
    LocalDateTime publishedAt = article.getPublishedAt();
    if ("PUBLISHED".equals(status) && publishedAt == null) {
      publishedAt = LocalDateTime.now();
    }

    article.setStatusCode(status);
    article.setPublishedAt(publishedAt);
    article.setUpdatedBy(updatedBy);
    article.setUpdatedAt(LocalDateTime.now());

    Article savedArticle = articleJpaRepository.save(article);
    log.info("Article status updated: {} -> {}", articleId, status);

    return articleMapper.toArticleResponse(savedArticle);
  }

  /**
   * Validates if the status is a valid article status.
   *
   * @param status the status to validate
   * @return true if valid, false otherwise
   */
  private boolean isValidStatus(String status) {
    return status.equals("DRAFT") || status.equals("PENDING")
      || status.equals("PUBLISHED") || status.equals("HIDDEN");
  }
}
