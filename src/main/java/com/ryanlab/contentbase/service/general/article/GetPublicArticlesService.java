package com.ryanlab.contentbase.service.general.article;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.model.dto.response.article.ArticleListResponse;
import com.ryanlab.contentbase.model.dto.response.article.ArticleSummaryResponse;
import com.ryanlab.contentbase.model.entity.Article;
import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetPublicArticlesService {
  final ArticleJpaRepository articleJpaRepository;

  /**
   * Retrieves a page of published articles based on the provided parameters.
   *
   * @param page the page number
   * @param size the page size
   * @param sortBy the field to sort by (default: publishedAt)
   * @param sortDirection the sort direction (ASC or DESC)
   * @return the article list response with pagination info
   */
  public ArticleListResponse getPublicArticles(Integer page, Integer size,
    String sortBy, String sortDirection) {
    if (page < 0 || size < 1) {
      throw new IllegalArgumentException(
        String.format("Invalid page or size: page: %d, size: %d", page, size));
    }

    Sort.Direction direction = Sort.Direction.valueOf(sortDirection.toUpperCase());
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Article> articlesPage =
      articleJpaRepository.getPublicArticles("PUBLISHED", pageable);

    return buildArticleListResponse(articlesPage, page, size);
  }

  /**
   * Searches articles by keyword.
   *
   * @param keyword the search keyword
   * @param page the page number
   * @param size the page size
   * @return the article list response with pagination info
   */
  public ArticleListResponse searchArticles(String keyword, Integer page,
    Integer size) {
    if (page < 0 || size < 1) {
      throw new IllegalArgumentException(
        String.format("Invalid page or size: page: %d, size: %d", page, size));
    }

    Pageable pageable = PageRequest.of(page, size);
    Page<Article> articlesPage =
      articleJpaRepository.searchArticles(keyword, pageable);

    return buildArticleListResponse(articlesPage, page, size);
  }

  /**
   * Retrieves articles by category code.
   *
   * @param categoryCode the category code
   * @param page the page number
   * @param size the page size
   * @return the article list response with pagination info
   */
  public ArticleListResponse getArticlesByCategory(String categoryCode,
    Integer page, Integer size) {
    if (page < 0 || size < 1) {
      throw new IllegalArgumentException(
        String.format("Invalid page or size: page: %d, size: %d", page, size));
    }

    Pageable pageable = PageRequest.of(page, size);
    Page<Article> articlesPage =
      articleJpaRepository.getArticlesByCategory(categoryCode, pageable);

    return buildArticleListResponse(articlesPage, page, size);
  }

  /**
   * Retrieves articles by author ID.
   *
   * @param authorId the author ID
   * @param page the page number
   * @param size the page size
   * @return the article list response with pagination info
   */
  public ArticleListResponse getArticlesByAuthor(String authorId, Integer page,
    Integer size) {
    if (page < 0 || size < 1) {
      throw new IllegalArgumentException(
        String.format("Invalid page or size: page: %d, size: %d", page, size));
    }

    Pageable pageable = PageRequest.of(page, size);
    Page<Article> articlesPage =
      articleJpaRepository.findByAuthorId(authorId, pageable);

    return buildArticleListResponse(articlesPage, page, size);
  }

  /**
   * Retrieves trending articles based on the provided limit.
   *
   * @param limit the maximum number of articles to retrieve
   * @return the article list response
   */
  public ArticleListResponse getTrendingArticles(Integer limit) {
    if (limit < 1) {
      throw new IllegalArgumentException(
        String.format("Invalid limit: %d", limit));
    }

    Pageable pageable = PageRequest.of(0, limit);
    Page<Article> articlesPage =
      articleJpaRepository.getTrendingArticles("PUBLISHED", pageable);

    return buildArticleListResponse(articlesPage, 0, limit);
  }

  /**
   * Builds an ArticleListResponse from a Page of Articles.
   *
   * @param articlesPage the page of articles
   * @param page the current page number
   * @param size the page size
   * @return the article list response
   */
  private ArticleListResponse buildArticleListResponse(Page<Article> articlesPage,
    Integer page, Integer size) {
    List<ArticleSummaryResponse> articleSummaries =
      articlesPage.getContent()
                  .stream()
                  .map(this::toArticleSummaryResponse)
                  .collect(Collectors.toList());

    return ArticleListResponse.builder()
                              .articles(articleSummaries)
                              .totalElements(articlesPage.getTotalElements())
                              .totalPages(articlesPage.getTotalPages())
                              .currentPage(page)
                              .pageSize(size)
                              .hasNext(articlesPage.hasNext())
                              .hasPrevious(articlesPage.hasPrevious())
                              .build();
  }

  /**
   * Converts an Article entity to an ArticleSummaryResponse.
   *
   * @param article the article entity
   * @return the article summary response
   */
  private ArticleSummaryResponse toArticleSummaryResponse(Article article) {
    return ArticleSummaryResponse.builder()
                                 .id(article.getId())
                                 .articleId(article.getArticleId())
                                 .title(article.getTitle())
                                 .slug(article.getSlug())
                                 .summary(article.getSummary())
                                 .authorId(article.getAuthorId())
                                 .avatarFileId(article.getAvatarFileId())
                                 .statusCode(article.getStatusCode())
                                 .publishedAt(article.getPublishedAt())
                                 .createdAt(article.getCreatedAt())
                                 .updatedAt(article.getUpdatedAt())
                                 .build();
  }
}
