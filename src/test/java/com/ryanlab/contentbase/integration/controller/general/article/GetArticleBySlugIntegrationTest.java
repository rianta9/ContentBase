package com.ryanlab.contentbase.integration.controller.general.article;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.ryanlab.contentbase.BaseIntegrationTest;
import com.ryanlab.contentbase.model.entity.Article;
import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;
import com.ryanlab.contentbase.test.fixture.ArticleTestFixture;

@AutoConfigureMockMvc
@DisplayName("Article Slug Endpoint Integration Tests")
public class GetArticleBySlugIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ArticleJpaRepository articleJpaRepository;

  private Article publishedArticle;

  @BeforeEach
  void setUp() {
    articleJpaRepository.deleteAll();
    
    Article article = ArticleTestFixture.createPublishedArticle();
    article.setTitle("Best Java Practices");
    article.setSlug("best-java-practices");
    publishedArticle = articleJpaRepository.saveAndFlush(article);
  }

  @Test
  @DisplayName("Should retrieve published article by slug successfully")
  void testGetArticleBySlugSuccess() throws Exception {
    mockMvc.perform(get("/api/public/articles/slug/{slug}", "best-java-practices"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.contentId").exists())
           .andExpect(jsonPath("$.title").value("Best Java Practices"))
           .andExpect(jsonPath("$.content").exists());
  }

  @Test
  @DisplayName("Should return 404 when article slug does not exist")
  void testGetArticleBySlugNotFound() throws Exception {
    mockMvc.perform(get("/api/public/articles/slug/{slug}", "non-existent-slug"))
           .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Should not return draft article by slug")
  void testGetArticleBySlugDraftNotReturned() throws Exception {
    Article draftArticle = ArticleTestFixture.createDraftArticle();
    draftArticle.setSlug("draft-test-article");
    articleJpaRepository.saveAndFlush(draftArticle);

    mockMvc.perform(get("/api/public/articles/slug/{slug}", "draft-test-article"))
           .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Should not return hidden article by slug")
  void testGetArticleBySlugHiddenNotReturned() throws Exception {
    Article hiddenArticle = ArticleTestFixture.createHiddenArticle();
    hiddenArticle.setSlug("hidden-test-article");
    articleJpaRepository.saveAndFlush(hiddenArticle);

    mockMvc.perform(get("/api/public/articles/slug/{slug}", "hidden-test-article"))
           .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Should handle slug with special characters")
  void testGetArticleBySlugSpecialCharacters() throws Exception {
    Article specialArticle = ArticleTestFixture.createPublishedArticle();
    specialArticle.setSlug("article-with-123-numbers");
    articleJpaRepository.saveAndFlush(specialArticle);

    mockMvc.perform(get("/api/public/articles/slug/{slug}", "article-with-123-numbers"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.title").exists());
  }

  @Test
  @DisplayName("Should return 404 for empty slug")
  void testGetArticleBySlugEmpty() throws Exception {
    mockMvc.perform(get("/api/public/articles/slug/{slug}", ""))
           .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Should be case-sensitive for slug matching")
  void testGetArticleBySlugCaseSensitive() throws Exception {
    mockMvc.perform(get("/api/public/articles/slug/{slug}", "BEST-JAVA-PRACTICES"))
           .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Should return full article response with all fields")
  void testGetArticleBySlugReturnAllFields() throws Exception {
    mockMvc.perform(get("/api/public/articles/slug/{slug}", "best-java-practices"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.contentId").exists())
           .andExpect(jsonPath("$.title").exists())
           .andExpect(jsonPath("$.dateCreated").exists())
           .andExpect(jsonPath("$.content").exists())
           .andExpect(jsonPath("$.categoryId").exists())
           .andExpect(jsonPath("$.categoryName").exists());
  }
}
