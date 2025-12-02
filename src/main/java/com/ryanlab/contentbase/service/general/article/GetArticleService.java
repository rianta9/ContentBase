package com.ryanlab.contentbase.service.general.article;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.core.exception.ResourceNotFoundException;
import com.ryanlab.contentbase.model.dto.response.content.ArticleResponse;
import com.ryanlab.contentbase.model.entity.Article;
import com.ryanlab.contentbase.model.mapper.ArticleMapper;
import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;
import com.ryanlab.contentbase.service.cached.RedisService;
import com.ryanlab.contentbase.service.general.articlestatistic.UpdateArticleStatisticService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetArticleService {
  final ArticleJpaRepository articleJpaRepository;
  final ArticleMapper articleMapper;
  final RedisService redisService;
  final UpdateArticleStatisticService updateArticleStatisticService;
  static int TOP_ARTICLES_SIZE = 10;
  static final String TOP_ARTICLES_CACHE_KEY_PREFIX = "top_articles:";

  /**
   * Retrieves an article by its ID.
   *
   * @param articleId the ID of the article to retrieve
   * @return the article response
   * @throws ResourceNotFoundException if the article is not found
   */
  public ArticleResponse getArticle(String articleId) {
    ArticleResponse result =
      articleJpaRepository.findById(articleId)
                          .map(articleMapper::toArticleResponse)
                          .orElseThrow(
                            () -> new ResourceNotFoundException(
                              String.format("Article Not Found: %s", articleId),
                              new Object[] { "Article" },
                              new Object[] {
                                String.format("ArticleId = %s", articleId) }));
    updateArticleStatisticService.incrementViewCount(articleId);
    return result;
  }

  /**
   * Retrieves a page of articles based on the provided parameters.
   *
   * @param page     the page number
   * @param size     the page size
   * @param category the category to filter by
   * @param sort     the sort criteria (e.g., "field,asc")
   * @return the page of article responses
   */
  public Page<ArticleResponse> getArticles(int page, int size, String category,
    String sort) {
    if (page < 0 || size < 1) {
      throw new IllegalArgumentException(
        String.format("Invalid page or size: page: %d, size: %d", page, size));
    }
    Sort sortObject = parseSortCriteria(sort);
    Pageable pageable = PageRequest.of(page, size, sortObject);
    Page<Article> articlesPage =
      articleJpaRepository.getPublicArticles(category, pageable);
    return articlesPage.map(articleMapper::toArticleResponse);
  }

  /**
   * Parses the sort criteria from the provided string.
   *
   * @param sort the sort criteria (e.g., "field,asc")
   * @return the parsed sort object
   */
  private Sort parseSortCriteria(String sort) {
    String[] parts = sort.split(",");
    if (parts.length != 2) {
      throw new IllegalArgumentException(
        String.format(
          "Invalid sort criteria: value: %s, size: %d, valid size: %d",
          sort,
          parts.length,
          2));
    }
    return Sort.by(Sort.Direction.valueOf(parts[1].toUpperCase()), parts[0]);
  }

  /**
   * Retrieves a page of articles based on the provided search keyword.
   *
   * @param keyword the search keyword
   * @param page    the page number
   * @param size    the page size
   * @return the page of article responses
   */
  public Page<ArticleResponse> search(String keyword, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Article> articlesPage =
      articleJpaRepository.searchArticles(keyword, pageable);
    List<ArticleResponse> articlesList =
      articlesPage.getContent()
                  .stream()
                  .map(articleMapper::toArticleResponse)
                  .collect(Collectors.toList());
    return new PageImpl<>(
      articlesList,
      pageable,
      articlesPage.getTotalElements());
  }

  /**
   * Retrieves the top articles based on the provided category and time
   * criteria.
   * 
   * @param category the category to filter by
   * @param time     the time criteria (e.g., "day", "week", "month")
   * @return the list of article responses
   */
  public List<ArticleResponse> getTopArticles(String category, String time) {
    String cacheKey = TOP_ARTICLES_CACHE_KEY_PREFIX + category + ":" + time;

    List<ArticleResponse> cached = redisService.get(cacheKey);
    if (cached != null) {
      return cached;
    }
    List<Article> topArticles =
      articleJpaRepository.getTopArticles(category, time, TOP_ARTICLES_SIZE);
    List<ArticleResponse> result =
      topArticles.stream()
                 .map(item -> articleMapper.toArticleResponse(item))
                 .collect(Collectors.toList());
    redisService.set(cacheKey, result, 3600);
    return result;
  }

}
