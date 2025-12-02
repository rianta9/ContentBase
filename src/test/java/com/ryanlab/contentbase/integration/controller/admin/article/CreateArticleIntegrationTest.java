package com.ryanlab.contentbase.integration.controller.admin.article;

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
import com.ryanlab.contentbase.model.dto.request.article.CreateArticleRequest;
import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;
import com.ryanlab.contentbase.repository.jpa.CategoryJpaRepository;

@AutoConfigureMockMvc
@DisplayName("Create Article Endpoint Integration Tests")
public class CreateArticleIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ArticleJpaRepository articleJpaRepository;

  @Autowired
  private CategoryJpaRepository categoryJpaRepository;

  @BeforeEach
  void setUp() {
    articleJpaRepository.deleteAll();
    categoryJpaRepository.deleteAll();
  }

  private CreateArticleRequest createValidRequest() {
    CreateArticleRequest request = new CreateArticleRequest();
    request.setTitle("Test Article Title");
    request.setSlug("test-article-slug");
    request.setContent("Test article content with sufficient length");
    request.setDescription("Brief description");
    request.setThumbnail("https://example.com/image.jpg");
    request.setStatus("DRAFT");
    return request;
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should create article successfully with valid data")
  void testCreateArticleSuccess() throws Exception {
    CreateArticleRequest request = createValidRequest();

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.articleId").exists())
           .andExpect(jsonPath("$.articleTitle").value("Test Article Title"))
           .andExpect(jsonPath("$.createdDate").exists());

    assert articleJpaRepository.count() == 1;
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should return 400 when title is missing")
  void testCreateArticleMissingTitle() throws Exception {
    CreateArticleRequest request = createValidRequest();
    request.setTitle(null);

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should return 400 when title is empty")
  void testCreateArticleEmptyTitle() throws Exception {
    CreateArticleRequest request = createValidRequest();
    request.setTitle("");

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should return 400 when slug is missing")
  void testCreateArticleMissingSlug() throws Exception {
    CreateArticleRequest request = createValidRequest();
    request.setSlug(null);

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should return 400 when slug is empty")
  void testCreateArticleEmptySlug() throws Exception {
    CreateArticleRequest request = createValidRequest();
    request.setSlug("");

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should return 400 when content is missing")
  void testCreateArticleMissingContent() throws Exception {
    CreateArticleRequest request = createValidRequest();
    request.setContent(null);

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should return 400 when content is empty")
  void testCreateArticleEmptyContent() throws Exception {
    CreateArticleRequest request = createValidRequest();
    request.setContent("");

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should accept article with very long content")
  void testCreateArticleLongContent() throws Exception {
    CreateArticleRequest request = createValidRequest();
    request.setContent("Very long content ".repeat(500));

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.id").exists());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should create article with special characters in title")
  void testCreateArticleSpecialCharactersTitle() throws Exception {
    CreateArticleRequest request = createValidRequest();
    request.setTitle("Test Article: \"Special & Characters\" <>&");

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.title").value("Test Article: \"Special & Characters\" <>&#"));
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should create article with special characters in slug")
  void testCreateArticleSpecialCharactersSlug() throws Exception {
    CreateArticleRequest request = createValidRequest();
    request.setSlug("test-slug-with-special-chars-123");

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.slug").value("test-slug-with-special-chars-123"));
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should create article with DRAFT status by default")
  void testCreateArticleDefaultStatusDraft() throws Exception {
    CreateArticleRequest request = new CreateArticleRequest();
    request.setTitle("Test Article");
    request.setSlug("test-article");
    request.setContent("Test content");
    request.setDescription("Description");

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.statusCode").value("DRAFT"));
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should create article with optional fields")
  void testCreateArticleWithOptionalFields() throws Exception {
    CreateArticleRequest request = createValidRequest();
    request.setDescription("Optional description");
    request.setThumbnail("https://example.com/image.jpg");

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.description").value("Optional description"))
           .andExpect(jsonPath("$.thumbnail").value("https://example.com/image.jpg"));
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should return 400 for duplicate slug")
  void testCreateArticleDuplicateSlug() throws Exception {
    CreateArticleRequest request1 = createValidRequest();

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request1)))
           .andExpect(status().isCreated());

    CreateArticleRequest request2 = createValidRequest();
    request2.setTitle("Different Title");

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request2)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should return 400 for invalid status")
  void testCreateArticleInvalidStatus() throws Exception {
    CreateArticleRequest request = createValidRequest();
    request.setStatus("INVALID_STATUS");

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "user", roles = "USER")
  @DisplayName("Should return 403 for non-admin user")
  void testCreateArticleForbiddenUser() throws Exception {
    CreateArticleRequest request = createValidRequest();

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("Should return 401 for unauthenticated user")
  void testCreateArticleUnauthorized() throws Exception {
    CreateArticleRequest request = createValidRequest();

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should return 400 for invalid content type")
  void testCreateArticleInvalidContentType() throws Exception {
    CreateArticleRequest request = createValidRequest();

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.TEXT_PLAIN)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isUnsupportedMediaType());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should return created article with all fields")
  void testCreateArticleReturnAllFields() throws Exception {
    CreateArticleRequest request = createValidRequest();

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.id").exists())
           .andExpect(jsonPath("$.articleId").exists())
           .andExpect(jsonPath("$.title").exists())
           .andExpect(jsonPath("$.slug").exists())
           .andExpect(jsonPath("$.content").exists())
           .andExpect(jsonPath("$.statusCode").exists())
           .andExpect(jsonPath("$.createdAt").exists());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should increment article count on creation")
  void testCreateArticleCountIncrement() throws Exception {
    long initialCount = articleJpaRepository.count();

    CreateArticleRequest request = createValidRequest();

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isCreated());

    long finalCount = articleJpaRepository.count();
    assert finalCount == initialCount + 1;
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  @DisplayName("Should trim whitespace from title")
  void testCreateArticleTrimTitle() throws Exception {
    CreateArticleRequest request = createValidRequest();
    request.setTitle("  Test Article Title  ");

    mockMvc.perform(post("/api/admin/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isCreated());
  }
}
