package com.ryanlab.contentbase.controller.general.article;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ryanlab.contentbase.model.dto.request.comment.CommentCreationRequest;
import com.ryanlab.contentbase.model.dto.response.article.ArticleListResponse;
import com.ryanlab.contentbase.model.dto.response.article.ArticleLikeResponse;
import com.ryanlab.contentbase.model.dto.response.comment.CommentResponse;
import com.ryanlab.contentbase.model.dto.response.content.ArticleResponse;
import com.ryanlab.contentbase.service.general.article.GetArticleBySlugService;
import com.ryanlab.contentbase.service.general.article.GetArticleService;
import com.ryanlab.contentbase.service.general.article.GetPublicArticlesService;
import com.ryanlab.contentbase.service.general.article.ToggleArticleLikeService;
import com.ryanlab.contentbase.service.general.comment.CreateCommentService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Public Article Controller Handles public read-only operations for articles
 */
@RestController
@RequestMapping("/api/public/articles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PublicArticleController {

    GetArticleService getArticleService;
    GetArticleBySlugService getArticleBySlugService;
    GetPublicArticlesService getPublicArticlesService;
    CreateCommentService createCommentService;
    ToggleArticleLikeService toggleArticleLikeService;

    /**
     * Get a single published article by ID GET /api/public/articles/{articleId}
     */
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponse>
        getArticle(@PathVariable String articleId) {
        ArticleResponse response = getArticleService.getArticle(articleId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a single published article by slug GET /api/public/articles/slug/{slug}
     */
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ArticleResponse>
        getArticleBySlug(@PathVariable String slug) {
        ArticleResponse response = getArticleBySlugService.getArticleBySlug(slug);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all published articles GET /api/public/articles
     */
    @GetMapping
    public ResponseEntity<ArticleListResponse> getPublicArticles(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size,
        @RequestParam(defaultValue = "publishedAt") String sortBy,
        @RequestParam(defaultValue = "DESC") String sortDirection) {
        ArticleListResponse response =
            getPublicArticlesService.getPublicArticles(
                page,
                size,
                sortBy,
                sortDirection);
        return ResponseEntity.ok(response);
    }

    /**
     * Search articles by keyword GET /api/public/articles/search
     */
    @GetMapping("/search")
    public ResponseEntity<ArticleListResponse> searchArticles(
        @RequestParam String keyword,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size) {
        ArticleListResponse response =
            getPublicArticlesService.searchArticles(keyword, page, size);
        return ResponseEntity.ok(response);
    }

    /**
     * Get articles by category GET /api/public/articles/category/{categoryCode}
     */
    @GetMapping("/category/{categoryCode}")
    public ResponseEntity<ArticleListResponse> getArticlesByCategory(
        @PathVariable String categoryCode,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size) {
        ArticleListResponse response =
            getPublicArticlesService.getArticlesByCategory(
                categoryCode,
                page,
                size);
        return ResponseEntity.ok(response);
    }

    /**
     * Get articles by author GET /api/public/articles/author/{authorId}
     */
    @GetMapping("/author/{authorId}")
    public ResponseEntity<ArticleListResponse> getArticlesByAuthor(
        @PathVariable String authorId,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size) {
        ArticleListResponse response =
            getPublicArticlesService.getArticlesByAuthor(authorId, page, size);
        return ResponseEntity.ok(response);
    }

    /**
     * Get trending articles GET /api/public/articles/trending
     */
    @GetMapping("/trending")
    public ResponseEntity<ArticleListResponse>
        getTrendingArticles(@RequestParam(defaultValue = "10") Integer limit) {
        ArticleListResponse response =
            getPublicArticlesService.getTrendingArticles(limit);
        return ResponseEntity.ok(response);
    }

    /**
     * Post a comment on an article POST /api/public/articles/{articleId}/comments
     */
    @PostMapping("/{articleId}/comments")
    public ResponseEntity<CommentResponse> createComment(
        @PathVariable String articleId,
        @RequestBody @Valid CommentCreationRequest request,
        Authentication auth) {
        String username = auth != null ? auth.getName() : "Anonymous";
        CommentResponse response =
            createCommentService.createComment(articleId, request, username);
        return ResponseEntity.ok(response);
    }

    /**
     * Like/unlike an article POST /api/public/articles/{articleId}/like
     */
    @PostMapping("/{articleId}/like")
    public ResponseEntity<ArticleLikeResponse> toggleLike(
        @PathVariable String articleId,
        Authentication auth) {
        String username = auth != null ? auth.getName() : "Anonymous";
        ArticleLikeResponse response =
            toggleArticleLikeService.toggleLike(articleId, username);
        return ResponseEntity.ok(response);
    }
}
