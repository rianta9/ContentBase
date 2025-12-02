package com.ryanlab.contentbase.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ryanlab.contentbase.core.repository.ICoreJpaRepository;
import com.ryanlab.contentbase.model.entity.Article;

@Repository
public interface ArticleJpaRepository
  extends ICoreJpaRepository<Article, String> {

  // Find articles by author
  Page<Article> findByAuthorId(String authorId, Pageable pageable);

  // Find articles by status
  Page<Article> findByStatusCode(String statusCode, Pageable pageable);

  // Get published articles
  @Query(value = "SELECT c FROM Article c WHERE c.statusCode = 'PUBLISHED'")
  Page<Article> getPublicArticles(String statusCode, Pageable pageable);

  // Search articles by keyword
  @Query(value = "SELECT c FROM Article c WHERE c.title LIKE %:keyword% OR c.content LIKE %:keyword%")
  Page<Article> searchArticles(@Param("keyword") String keyword,
    Pageable pageable);

  // Get top articles by views
  @Query(value = "SELECT c FROM Article c JOIN ArticleStatistic s ON c.id = s.articleId ORDER BY s.numberOfViews DESC")
  List<Article> getTopArticles(@Param("statusCode") String statusCode,
    @Param("categoryId") String categoryId, @Param("limit") int limit);

  // Get articles by category code
  @Query(value = "SELECT c FROM Article c JOIN ArticleCategory ac ON c.articleId = ac.articleId WHERE ac.categoryCode = :categoryCode AND c.statusCode = 'PUBLISHED'")
  Page<Article> getArticlesByCategory(@Param("categoryCode") String categoryCode,
    Pageable pageable);

  // Get trending articles
  @Query(value = "SELECT c FROM Article c WHERE c.statusCode = :statusCode ORDER BY c.publishedAt DESC")
  Page<Article> getTrendingArticles(@Param("statusCode") String statusCode,
    Pageable pageable);

  // Find articles by slug and published status
  Optional<Article> findBySlugAndStatusCode(String slug, String statusCode);
}
