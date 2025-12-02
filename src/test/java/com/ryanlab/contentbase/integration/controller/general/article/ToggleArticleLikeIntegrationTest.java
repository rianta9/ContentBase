package com.ryanlab.contentbase.integration.controller.general.article;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.ryanlab.contentbase.BaseIntegrationTest;
import com.ryanlab.contentbase.model.entity.Article;
import com.ryanlab.contentbase.model.entity.ArticleStatistic;
import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;
import com.ryanlab.contentbase.repository.jpa.ArticleStatisticJpaRepository;
import com.ryanlab.contentbase.test.fixture.ArticleTestFixture;

@AutoConfigureMockMvc
@DisplayName("Like Endpoint Integration Tests")
public class ToggleArticleLikeIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ArticleJpaRepository articleJpaRepository;

  @Autowired
  private ArticleStatisticJpaRepository articleStatisticJpaRepository;

  private Article testArticle;

  @BeforeEach
  void setUp() {
    articleStatisticJpaRepository.deleteAll();
    articleJpaRepository.deleteAll();

    testArticle = articleJpaRepository.saveAndFlush(
      ArticleTestFixture.createPublishedArticle());

    ArticleStatistic stats = ArticleStatistic.builder()
                                             .id(testArticle.getArticleId())
                                             .numberOfViews(0L)
                                             .numberOfReviews(0L)
                                             .numberOfFavourites(0L)
                                             .build();
    articleStatisticJpaRepository.saveAndFlush(stats);
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should like article successfully on first toggle")
  void testToggleLikeFirstTime() throws Exception {
    mockMvc.perform(post("/api/public/articles/{id}/like", testArticle.getId()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.articleId").value(testArticle.getId()))
           .andExpect(jsonPath("$.isLiked").value(true))
           .andExpect(jsonPath("$.likeCount").value(1));
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should unlike article on second toggle")
  void testToggleLikeTwice() throws Exception {
    // First like
    mockMvc.perform(post("/api/public/articles/{id}/like", testArticle.getId()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.isLiked").value(true));

    // Second like (unlike)
    mockMvc.perform(post("/api/public/articles/{id}/like", testArticle.getId()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.isLiked").value(false))
           .andExpect(jsonPath("$.likeCount").value(0));
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should return 404 when article does not exist")
  void testToggleLikeArticleNotFound() throws Exception {
    mockMvc.perform(post("/api/public/articles/{id}/like", "non-existent-id"))
           .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = "user1")
  void testToggleLikeMultipleUsers() throws Exception {
    // User 1 likes
    mockMvc.perform(post("/api/public/articles/{id}/like", testArticle.getId()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.isLiked").value(true))
           .andExpect(jsonPath("$.likeCount").value(1));
  }

  @Test
  @WithMockUser(username = "user2")
  void testToggleLikeAnotherUser() throws Exception {
    // User 2 likes (should increment count independently)
    mockMvc.perform(post("/api/public/articles/{id}/like", testArticle.getId()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.isLiked").value(true))
           .andExpect(jsonPath("$.likeCount").value(1));
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should prevent duplicate likes from same user")
  void testPreventDuplicateLikes() throws Exception {
    // First like
    mockMvc.perform(post("/api/public/articles/{id}/like", testArticle.getId()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.likeCount").value(1));

    // Try to like again immediately (should be recognized as already liked)
    mockMvc.perform(post("/api/public/articles/{id}/like", testArticle.getId()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.isLiked").value(false))
           .andExpect(jsonPath("$.likeCount").value(0));
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should handle rapid like/unlike toggles")
  void testRapidToggleLikes() throws Exception {
    for (int i = 0; i < 5; i++) {
      mockMvc.perform(post("/api/public/articles/{id}/like", testArticle.getId()))
             .andExpect(status().isOk());
    }

    // After 5 toggles (odd number), should be liked
    mockMvc.perform(post("/api/public/articles/{id}/like", testArticle.getId()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.isLiked").value(true));
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should return correct like count after multiple likes")
  void testLikeCountAccuracy() throws Exception {
    // Like from user1
    mockMvc.perform(post("/api/public/articles/{id}/like", testArticle.getId()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.likeCount").value(1));

    // Unlike from user1
    mockMvc.perform(post("/api/public/articles/{id}/like", testArticle.getId()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.likeCount").value(0));

    // Like again from user1
    mockMvc.perform(post("/api/public/articles/{id}/like", testArticle.getId()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.likeCount").value(1));
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should return response with all required fields")
  void testToggleLikeResponseFields() throws Exception {
    mockMvc.perform(post("/api/public/articles/{id}/like", testArticle.getId()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.articleId").exists())
           .andExpect(jsonPath("$.isLiked").exists())
           .andExpect(jsonPath("$.likeCount").exists());
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should update database after like")
  void testToggleLikePersistToDB() throws Exception {
    mockMvc.perform(post("/api/public/articles/{id}/like", testArticle.getId()))
           .andExpect(status().isOk());

    ArticleStatistic stats = articleStatisticJpaRepository.findById(testArticle.getArticleId())
                                                           .orElseThrow();
    assert stats.getNumberOfFavourites() == 1L;
  }
}
