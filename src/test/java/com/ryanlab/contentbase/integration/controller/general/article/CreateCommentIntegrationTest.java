package com.ryanlab.contentbase.integration.controller.general.article;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryanlab.contentbase.BaseIntegrationTest;
import com.ryanlab.contentbase.model.dto.request.comment.CommentCreationRequest;
import com.ryanlab.contentbase.model.entity.Article;
import com.ryanlab.contentbase.model.entity.ArticleStatistic;
import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;
import com.ryanlab.contentbase.repository.jpa.ArticleStatisticJpaRepository;
import com.ryanlab.contentbase.repository.jpa.CommentJpaRepository;
import com.ryanlab.contentbase.test.fixture.ArticleTestFixture;

@AutoConfigureMockMvc
@DisplayName("Comments Endpoint Integration Tests")
public class CreateCommentIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ArticleJpaRepository articleJpaRepository;

  @Autowired
  private CommentJpaRepository commentJpaRepository;

  @Autowired
  private ArticleStatisticJpaRepository articleStatisticJpaRepository;

  private Article testArticle;

  @BeforeEach
  void setUp() {
    commentJpaRepository.deleteAll();
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
  @DisplayName("Should create comment successfully")
  void testCreateCommentSuccess() throws Exception {
    CommentCreationRequest request = new CommentCreationRequest();
    request.setContent("This is a great article!");
    request.setArticleId(testArticle.getId());

    mockMvc.perform(post("/api/public/articles/{id}/comments", testArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.articleId").value(testArticle.getId()))
           .andExpect(jsonPath("$.content").value("This is a great article!"))
           .andExpect(jsonPath("$.username").value("testuser"));
  }

  @Test
  @WithMockUser(username = "anonymoususer")
  @DisplayName("Should create comment without authentication")
  void testCreateCommentAnonymous() throws Exception {
    CommentCreationRequest request = new CommentCreationRequest();
    request.setContent("Anonymous comment");
    request.setArticleId(testArticle.getId());

    mockMvc.perform(post("/api/public/articles/{id}/comments", testArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.content").value("Anonymous comment"));
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should return 404 when article does not exist")
  void testCreateCommentArticleNotFound() throws Exception {
    CommentCreationRequest request = new CommentCreationRequest();
    request.setContent("Comment on non-existent article");
    request.setArticleId("non-existent-id");

    mockMvc.perform(post("/api/public/articles/{id}/comments", "non-existent-id")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should return 400 when comment content is empty")
  void testCreateCommentEmptyContent() throws Exception {
    CommentCreationRequest request = new CommentCreationRequest();
    request.setContent("");
    request.setArticleId(testArticle.getId());

    mockMvc.perform(post("/api/public/articles/{id}/comments", testArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should return 400 when comment content is null")
  void testCreateCommentNullContent() throws Exception {
    CommentCreationRequest request = new CommentCreationRequest();
    request.setContent(null);
    request.setArticleId(testArticle.getId());

    mockMvc.perform(post("/api/public/articles/{id}/comments", testArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should handle very long comment")
  void testCreateCommentLongContent() throws Exception {
    String longContent = "A".repeat(1000);
    CommentCreationRequest request = new CommentCreationRequest();
    request.setContent(longContent);
    request.setArticleId(testArticle.getId());

    mockMvc.perform(post("/api/public/articles/{id}/comments", testArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.content").value(longContent));
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should return 400 when comment exceeds max length")
  void testCreateCommentExceedsMaxLength() throws Exception {
    String tooLongContent = "A".repeat(1001);
    CommentCreationRequest request = new CommentCreationRequest();
    request.setContent(tooLongContent);
    request.setArticleId(testArticle.getId());

    mockMvc.perform(post("/api/public/articles/{id}/comments", testArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should increment comment count in article statistics")
  void testCreateCommentIncrementsStats() throws Exception {
    CommentCreationRequest request = new CommentCreationRequest();
    request.setContent("Test comment");
    request.setArticleId(testArticle.getId());

    mockMvc.perform(post("/api/public/articles/{id}/comments", testArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk());

    ArticleStatistic stats = articleStatisticJpaRepository.findById(testArticle.getArticleId())
                                                           .orElseThrow();
    assert stats.getNumberOfReviews() == 1L;
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should handle comment with special characters")
  void testCreateCommentSpecialCharacters() throws Exception {
    String specialContent = "Great article! @#$% & \"quoted\" text's here";
    CommentCreationRequest request = new CommentCreationRequest();
    request.setContent(specialContent);
    request.setArticleId(testArticle.getId());

    mockMvc.perform(post("/api/public/articles/{id}/comments", testArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.content").value(specialContent));
  }

  @Test
  @WithMockUser(username = "testuser")
  @DisplayName("Should create multiple comments on same article")
  void testCreateMultipleComments() throws Exception {
    for (int i = 0; i < 3; i++) {
      CommentCreationRequest request = new CommentCreationRequest();
      request.setContent("Comment " + i);
      request.setArticleId(testArticle.getId());

      mockMvc.perform(post("/api/public/articles/{id}/comments", testArticle.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(request)))
             .andExpect(status().isOk());
    }

    ArticleStatistic stats = articleStatisticJpaRepository.findById(testArticle.getArticleId())
                                                           .orElseThrow();
    assert stats.getNumberOfReviews() == 3L;
  }
}
