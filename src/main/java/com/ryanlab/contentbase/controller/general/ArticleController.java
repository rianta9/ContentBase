package com.ryanlab.contentbase.controller.general;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ryanlab.contentbase.model.dto.response.content.ArticleResponse;
import com.ryanlab.contentbase.service.general.article.GetArticleService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArticleController {
  GetArticleService getArticleService;

  @GetMapping(value = "/general/article/{articleId}", produces = "application/json")
  public ResponseEntity<ArticleResponse>
    getArticle(@PathVariable(required = true) @Valid String articleId) {
    System.out.println("getArticle:" + articleId);
    ArticleResponse response = getArticleService.getArticle(articleId);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/general/article/list")
  public ResponseEntity<Page<ArticleResponse>> getArticles(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(required = false) String category,
    @RequestParam(defaultValue = "created_at,desc") String sort) {
    Page<ArticleResponse> response =
      getArticleService.getArticles(page, size, category, sort);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/general/article/top")
  public ResponseEntity<List<ArticleResponse>> getTopArticles(
    @RequestParam(required = false) String category,
    @RequestParam(defaultValue = "day") String time) {
    List<ArticleResponse> response =
      getArticleService.getTopArticles(category, time);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/general/article/search")
  public ResponseEntity<Page<ArticleResponse>> searchArticles(
    @RequestParam String keyword, @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
    Page<ArticleResponse> response =
      getArticleService.search(keyword, page, size);
    return ResponseEntity.ok(response);
  }

}
