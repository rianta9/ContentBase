package com.ryanlab.contentbase.service.admin;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.configuration.session.UserContext;
import com.ryanlab.contentbase.core.util.RandomUUID;
import com.ryanlab.contentbase.model.dto.request.ArticleCreationRequest;
import com.ryanlab.contentbase.model.dto.response.content.CreateArticleResponse;
import com.ryanlab.contentbase.model.entity.Article;
import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateArticleService {
  ArticleJpaRepository articleJpaRepository;
  UserContext userContext;

  public CreateArticleResponse handleRequest(ArticleCreationRequest input) {
    Article article = Article.builder()
                             .title(input.getTitle())
                             .content(input.getContent())
                             .build();
    article.setId(RandomUUID.getRandomID());
    article.setCreatedBy(userContext.getUsername());
    article.setUpdatedBy(userContext.getUsername());
    article.setCreatedAt(LocalDateTime.now());
    article.setUpdatedAt(LocalDateTime.now());
    Article result = articleJpaRepository.saveAndFlush(article);
    return CreateArticleResponse.builder().articleId(result.getId()).build();
  }

}
