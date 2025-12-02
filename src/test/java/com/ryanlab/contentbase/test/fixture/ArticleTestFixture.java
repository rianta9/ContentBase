package com.ryanlab.contentbase.test.fixture;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ryanlab.contentbase.core.entity.CoreAudit;
import com.ryanlab.contentbase.model.entity.Article;

public class ArticleTestFixture {

  private static String generateUniqueSlug() {
    return "article-" + UUID.randomUUID().toString().substring(0, 8);
  }

  public static Article createPublishedArticle() {
    String uniqueSlug = generateUniqueSlug();
    LocalDateTime now = LocalDateTime.now();
    Article article = Article.builder()
                  .articleId(UUID.randomUUID().toString())
                  .title("Test Article Title " + uniqueSlug.substring(0, 8))
                  .slug(uniqueSlug)
                  .summary("Test article summary")
                  .content("Test article content")
                  .authorId(UUID.randomUUID().toString())
                  .avatarFileId(null)
                  .statusCode("PUBLISHED")
                  .publishedAt(now)
                  .build();
    article.setVersion(null);
    article.setCreatedBy("test-user");
    article.setUpdatedBy("test-user");
    article.setCreatedAt(now);
    article.setUpdatedAt(now);
    return article;
  }

  public static Article createDraftArticle() {
    String uniqueSlug = generateUniqueSlug();
    LocalDateTime now = LocalDateTime.now();
    Article article = Article.builder()
                  .articleId(UUID.randomUUID().toString())
                  .title("Draft Article " + uniqueSlug.substring(0, 8))
                  .slug(uniqueSlug)
                  .summary("Draft summary")
                  .content("Draft content")
                  .authorId(UUID.randomUUID().toString())
                  .statusCode("DRAFT")
                  .build();
    article.setVersion(null);
    article.setCreatedBy("test-user");
    article.setUpdatedBy("test-user");
    article.setCreatedAt(now);
    article.setUpdatedAt(now);
    return article;
  }

  public static Article createHiddenArticle() {
    String uniqueSlug = generateUniqueSlug();
    LocalDateTime now = LocalDateTime.now();
    Article article = Article.builder()
                  .articleId(UUID.randomUUID().toString())
                  .title("Hidden Article " + uniqueSlug.substring(0, 8))
                  .slug(uniqueSlug)
                  .summary("Hidden summary")
                  .content("Hidden content")
                  .authorId(UUID.randomUUID().toString())
                  .statusCode("HIDDEN")
                  .build();
    article.setVersion(null);
    article.setCreatedBy("test-user");
    article.setUpdatedBy("test-user");
    article.setCreatedAt(now);
    article.setUpdatedAt(now);
    return article;
  }
}
