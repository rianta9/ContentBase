package com.ryanlab.contentbase.integration.controller.admin.article;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.ryanlab.contentbase.model.dto.request.article.UpdateArticleStatusRequest;
import com.ryanlab.contentbase.model.entity.Article;
import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;
import com.ryanlab.contentbase.test.fixture.ArticleTestFixture;

@AutoConfigureMockMvc
@DisplayName("Article Status Update Endpoint Integration Tests")
public class UpdateArticleStatusIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ArticleJpaRepository articleJpaRepository;

  private Article draftArticle;

  @BeforeEach
  void setUp() {
    articleJpaRepository.deleteAll();

    draftArticle = articleJpaRepository.saveAndFlush(
      ArticleTestFixture.createDraftArticle());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should update article status to PUBLISHED successfully")
  void testUpdateStatusToPublished() throws Exception {
    UpdateArticleStatusRequest request = new UpdateArticleStatusRequest();
    request.setStatus("PUBLISHED");

    mockMvc.perform(put("/api/admin/articles/{id}/status", draftArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.contentId").exists())
           .andExpect(jsonPath("$.title").exists());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should update article status to DRAFT")
  void testUpdateStatusToDraft() throws Exception {
    Article publishedArticle = articleJpaRepository.saveAndFlush(
      ArticleTestFixture.createPublishedArticle());

    UpdateArticleStatusRequest request = new UpdateArticleStatusRequest();
    request.setStatus("DRAFT");

    mockMvc.perform(put("/api/admin/articles/{id}/status", publishedArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.contentId").exists());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should update article status to PENDING")
  void testUpdateStatusToPending() throws Exception {
    UpdateArticleStatusRequest request = new UpdateArticleStatusRequest();
    request.setStatus("PENDING");

    mockMvc.perform(put("/api/admin/articles/{id}/status", draftArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.contentId").exists());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should update article status to HIDDEN")
  void testUpdateStatusToHidden() throws Exception {
    UpdateArticleStatusRequest request = new UpdateArticleStatusRequest();
    request.setStatus("HIDDEN");

    mockMvc.perform(put("/api/admin/articles/{id}/status", draftArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.contentId").exists());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should return 400 for invalid status")
  void testUpdateStatusInvalid() throws Exception {
    UpdateArticleStatusRequest request = new UpdateArticleStatusRequest();
    request.setStatus("INVALID_STATUS");

    mockMvc.perform(put("/api/admin/articles/{id}/status", draftArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should return 400 when status is empty")
  void testUpdateStatusEmpty() throws Exception {
    UpdateArticleStatusRequest request = new UpdateArticleStatusRequest();
    request.setStatus("");

    mockMvc.perform(put("/api/admin/articles/{id}/status", draftArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should return 400 when status is null")
  void testUpdateStatusNull() throws Exception {
    UpdateArticleStatusRequest request = new UpdateArticleStatusRequest();
    request.setStatus(null);

    mockMvc.perform(put("/api/admin/articles/{id}/status", draftArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should return 404 when article does not exist")
  void testUpdateStatusArticleNotFound() throws Exception {
    UpdateArticleStatusRequest request = new UpdateArticleStatusRequest();
    request.setStatus("PUBLISHED");

    mockMvc.perform(put("/api/admin/articles/{id}/status", "non-existent-id")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = "user", roles = "USER")
  @DisplayName("Should return 403 for non-admin user")
  void testUpdateStatusForbiddenUser() throws Exception {
    UpdateArticleStatusRequest request = new UpdateArticleStatusRequest();
    request.setStatus("PUBLISHED");

    mockMvc.perform(put("/api/admin/articles/{id}/status", draftArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("Should return 401 for unauthenticated user")
  void testUpdateStatusUnauthorized() throws Exception {
    UpdateArticleStatusRequest request = new UpdateArticleStatusRequest();
    request.setStatus("PUBLISHED");

    mockMvc.perform(put("/api/admin/articles/{id}/status", draftArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should set publishedAt when publishing")
  void testUpdateStatusPublishedAtSet() throws Exception {
    UpdateArticleStatusRequest request = new UpdateArticleStatusRequest();
    request.setStatus("PUBLISHED");

    mockMvc.perform(put("/api/admin/articles/{id}/status", draftArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.publishedAt").exists());

    Article updated = articleJpaRepository.findById(draftArticle.getId()).orElseThrow();
    assert updated.getPublishedAt() != null;
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should preserve publishedAt when changing from PUBLISHED to DRAFT")
  void testUpdateStatusPreservePublishedAt() throws Exception {
    Article publishedArticle = articleJpaRepository.saveAndFlush(
      ArticleTestFixture.createPublishedArticle());

    UpdateArticleStatusRequest request = new UpdateArticleStatusRequest();
    request.setStatus("DRAFT");

    mockMvc.perform(put("/api/admin/articles/{id}/status", publishedArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk());

    Article updated = articleJpaRepository.findById(publishedArticle.getId()).orElseThrow();
    assert updated.getPublishedAt() != null;
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should be case-insensitive for status")
  void testUpdateStatusCaseInsensitive() throws Exception {
    UpdateArticleStatusRequest request = new UpdateArticleStatusRequest();
    request.setStatus("published");

    mockMvc.perform(put("/api/admin/articles/{id}/status", draftArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.statusCode").value("PUBLISHED"));
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should return updated article with all fields")
  void testUpdateStatusReturnAllFields() throws Exception {
    UpdateArticleStatusRequest request = new UpdateArticleStatusRequest();
    request.setStatus("PUBLISHED");

    mockMvc.perform(put("/api/admin/articles/{id}/status", draftArticle.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").exists())
           .andExpect(jsonPath("$.articleId").exists())
           .andExpect(jsonPath("$.title").exists())
           .andExpect(jsonPath("$.statusCode").exists())
           .andExpect(jsonPath("$.publishedAt").exists());
  }
}
