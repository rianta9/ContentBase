package com.ryanlab.contentbase.controller.user.article;

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
import com.ryanlab.contentbase.model.dto.request.comment.CommentCreationRequest;
import com.ryanlab.contentbase.model.dto.response.article.ArticleListResponse;
import com.ryanlab.contentbase.model.dto.response.content.ArticleResponse;
import com.ryanlab.contentbase.model.dto.response.content.CreateArticleResponse;
import com.ryanlab.contentbase.service.admin.CreateArticleService;
import com.ryanlab.contentbase.service.user.article.DeleteOwnArticleService;
import com.ryanlab.contentbase.service.user.article.GetOwnArticlesService;
import com.ryanlab.contentbase.service.user.article.UpdateOwnArticleService;
import com.ryanlab.contentbase.service.user.comment.CommentService;
import com.ryanlab.contentbase.service.user.favourite.UserFavouriteService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * User Article Controller Handles user operations for their own articles
 */
@RestController
@RequestMapping("/api/user/articles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasRole('USER')")
public class UserArticleController {

  CreateArticleService createArticleService;
  UpdateOwnArticleService updateOwnArticleService;
  DeleteOwnArticleService deleteOwnArticleService;
  GetOwnArticlesService getOwnArticlesService;
  UserFavouriteService userFavouriteService;
  CommentService commentService;

  /**
   * Create a new article (draft) POST /api/user/articles
   */
  @PostMapping
  public ResponseEntity<CreateArticleResponse> createArticle(
    @RequestBody @Valid ArticleCreationRequest request, Authentication auth) {
    CreateArticleResponse response =
      createArticleService.handleRequest(request);
    return ResponseEntity.ok(response);
  }

  /**
   * Update own article PUT /api/user/articles/{articleId}
   */
  @PutMapping("/{articleId}")
  public ResponseEntity<ArticleResponse> updateOwnArticle(
    @PathVariable String articleId,
    @RequestBody @Valid UpdateArticleRequest request, Authentication auth) {
    request.setArticleId(articleId);
    String username = auth.getName();
    ArticleResponse response =
      updateOwnArticleService.updateOwnArticle(request, username);
    return ResponseEntity.ok(response);
  }

  /**
   * Delete own article DELETE /api/user/articles/{articleId}
   */
  @DeleteMapping("/{articleId}")
  public ResponseEntity<Void> deleteOwnArticle(@PathVariable String articleId,
    Authentication auth) {
    String username = auth.getName();
    deleteOwnArticleService.deleteOwnArticle(articleId, username);
    return ResponseEntity.noContent().build();
  }

  /**
   * Get own articles GET /api/user/articles
   */
  @GetMapping
  public ResponseEntity<ArticleListResponse> getOwnArticles(
    @RequestParam(defaultValue = "0") Integer page,
    @RequestParam(defaultValue = "20") Integer size,
    @RequestParam(defaultValue = "createdAt") String sortBy,
    @RequestParam(defaultValue = "DESC") String sortDirection,
    Authentication auth) {
    String username = auth.getName();
    ArticleListResponse response = getOwnArticlesService.getOwnArticles(
      username,
      page,
      size,
      sortBy,
      sortDirection);
    return ResponseEntity.ok(response);
  }

  /**
   * Add article to favorites POST /api/user/articles/{articleId}/favourite
   */
  @PostMapping("/{articleId}/favourite")
  public ResponseEntity<String> addToFavourites(@PathVariable String articleId,
    Authentication auth) {
    String username = auth.getName();
    userFavouriteService.addUserFavourite(username, articleId);
    return ResponseEntity.ok("Added to favourites");
  }

  /**
   * Remove article from favorites DELETE
   * /api/user/articles/{articleId}/favourite
   */
  @DeleteMapping("/{articleId}/favourite")
  public ResponseEntity<String>
    removeFromFavourites(@PathVariable String articleId, Authentication auth) {
    String username = auth.getName();
    userFavouriteService.deleteUserFavourite(username, articleId);
    return ResponseEntity.ok("Removed from favourites");
  }

  /**
   * Add comment to article POST /api/user/articles/{articleId}/comments
   */
  @PostMapping("/{articleId}/comments")
  public ResponseEntity<String> addComment(@PathVariable String articleId,
    @RequestBody @Valid CommentCreationRequest request, Authentication auth) {
    String username = auth.getName();
    commentService.addComment(request, username);
    return ResponseEntity.ok("Comment added");
  }

  /**
   * Delete own comment DELETE /api/user/articles/comments/{commentId}
   */
  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity<String> deleteComment(@PathVariable String commentId,
    Authentication auth) {
    String username = auth.getName();
    commentService.deleteComment(commentId, username);
    return ResponseEntity.ok("Comment deleted");
  }
}
