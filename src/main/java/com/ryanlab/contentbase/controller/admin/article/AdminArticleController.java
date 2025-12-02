package com.ryanlab.contentbase.controller.admin.article;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ryanlab.contentbase.model.dto.request.ArticleCreationRequest;
import com.ryanlab.contentbase.model.dto.request.article.UpdateArticleRequest;
import com.ryanlab.contentbase.model.dto.request.article.UpdateArticleStatusRequest;
import com.ryanlab.contentbase.model.dto.response.article.ArticleListResponse;
import com.ryanlab.contentbase.model.dto.response.content.ArticleResponse;
import com.ryanlab.contentbase.model.dto.response.content.CreateArticleResponse;
import com.ryanlab.contentbase.service.admin.CreateArticleService;
import com.ryanlab.contentbase.service.admin.article.DeleteArticleService;
import com.ryanlab.contentbase.service.admin.article.GetAllArticlesAdminService;
import com.ryanlab.contentbase.service.admin.article.PublishArticleService;
import com.ryanlab.contentbase.service.admin.article.UpdateArticleService;
import com.ryanlab.contentbase.service.admin.article.UpdateArticleStatusService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Admin Article Controller Handles all admin operations for articles
 */
@RestController
@RequestMapping("/api/admin/articles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasRole('ADMIN')")
public class AdminArticleController {

  CreateArticleService createArticleService;
  UpdateArticleService updateArticleService;
  UpdateArticleStatusService updateArticleStatusService;
  DeleteArticleService deleteArticleService;
  PublishArticleService publishArticleService;
  GetAllArticlesAdminService getAllArticlesAdminService;

  /**
   * Create a new article POST /api/admin/articles
   */
  @PostMapping
  public ResponseEntity<CreateArticleResponse> createArticle(
    @RequestBody @Valid ArticleCreationRequest request, Authentication auth) {
    CreateArticleResponse response =
      createArticleService.handleRequest(request);
    return ResponseEntity.ok(response);
  }

  /**
   * Update an existing article PUT /api/admin/articles/{articleId}
   */
  @PutMapping("/{articleId}")
  public ResponseEntity<ArticleResponse> updateArticle(
    @PathVariable String articleId,
    @RequestBody @Valid UpdateArticleRequest request, Authentication auth) {
    request.setArticleId(articleId);
    String updatedBy = auth.getName();
    ArticleResponse response =
      updateArticleService.updateArticle(request, updatedBy);
    return ResponseEntity.ok(response);
  }

  /**
   * Delete an article DELETE /api/admin/articles/{articleId}
   */
  @DeleteMapping("/{articleId}")
  public ResponseEntity<Void> deleteArticle(@PathVariable String articleId) {
    deleteArticleService.deleteArticle(articleId);
    return ResponseEntity.noContent().build();
  }

  /**
   * Publish an article POST /api/admin/articles/{articleId}/publish
   */
  @PostMapping("/{articleId}/publish")
  public ResponseEntity<ArticleResponse>
    publishArticle(@PathVariable String articleId, Authentication auth) {
    String publishedBy = auth.getName();
    ArticleResponse response =
      publishArticleService.publishArticle(articleId, publishedBy);
    return ResponseEntity.ok(response);
  }

  /**
   * Unpublish an article POST /api/admin/articles/{articleId}/unpublish
   */
  @PostMapping("/{articleId}/unpublish")
  public ResponseEntity<ArticleResponse>
    unpublishArticle(@PathVariable String articleId, Authentication auth) {
    String unpublishedBy = auth.getName();
    ArticleResponse response =
      publishArticleService.unpublishArticle(articleId, unpublishedBy);
    return ResponseEntity.ok(response);
  }

  /**
   * Update article status PUT /api/admin/articles/{articleId}/status
   */
  @PutMapping("/{articleId}/status")
  public ResponseEntity<ArticleResponse> updateArticleStatus(
    @PathVariable String articleId,
    @RequestBody @Valid UpdateArticleStatusRequest request,
    Authentication auth) {
    String updatedBy = auth.getName();
    ArticleResponse response =
      updateArticleStatusService.updateArticleStatus(articleId, request, updatedBy);
    return ResponseEntity.ok(response);
  }

  /**
   * Get all articles (including drafts) GET /api/admin/articles
   */
  @GetMapping
  public ResponseEntity<ArticleListResponse> getAllArticles(
    @RequestParam(defaultValue = "0") Integer page,
    @RequestParam(defaultValue = "20") Integer size,
    @RequestParam(defaultValue = "createdAt") String sortBy,
    @RequestParam(defaultValue = "DESC") String sortDirection) {
    ArticleListResponse response = getAllArticlesAdminService.getAllArticles(
      page,
      size,
      sortBy,
      sortDirection);
    return ResponseEntity.ok(response);
  }
}
