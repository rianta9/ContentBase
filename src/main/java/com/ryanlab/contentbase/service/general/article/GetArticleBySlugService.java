package com.ryanlab.contentbase.service.general.article;

import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.core.exception.ResourceNotFoundException;
import com.ryanlab.contentbase.model.dto.response.content.ArticleResponse;
import com.ryanlab.contentbase.model.entity.Article;
import com.ryanlab.contentbase.model.mapper.ArticleMapper;
import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;
import com.ryanlab.contentbase.service.general.articlestatistic.UpdateArticleStatisticService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetArticleBySlugService {
  final ArticleJpaRepository articleJpaRepository;
  final ArticleMapper articleMapper;
  final UpdateArticleStatisticService updateArticleStatisticService;

  /**
   * Retrieves a published article by its slug.
   *
   * @param slug the article slug
   * @return the article response
   * @throws ResourceNotFoundException if the article is not found or not published
   */
  public ArticleResponse getArticleBySlug(String slug) {
    Article article =
      articleJpaRepository.findBySlugAndStatusCode(slug, "PUBLISHED")
                          .orElseThrow(
                            () -> new ResourceNotFoundException(
                              String.format("Article Not Found: %s", slug),
                              new Object[] { "Article" },
                              new Object[] {
                                String.format("slug = %s", slug) }));

    updateArticleStatisticService.incrementViewCount(article.getArticleId());
    return articleMapper.toArticleResponse(article);
  }
}
