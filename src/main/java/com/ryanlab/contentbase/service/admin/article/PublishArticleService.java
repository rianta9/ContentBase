package com.ryanlab.contentbase.service.admin.article;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.model.dto.response.content.ArticleResponse;
import com.ryanlab.contentbase.model.entity.Article;
import com.ryanlab.contentbase.model.mapper.ArticleMapper;
import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PublishArticleService {
    ArticleJpaRepository articleJpaRepository;
    ArticleMapper articleMapper;

    public ArticleResponse publishArticle(@NonNull String articleId,
        @NonNull String publishedBy) {
        Article article =
            articleJpaRepository.findById(articleId)
                                .orElseThrow(
                                    () -> new RuntimeException(
                                        String.format(
                                            "Article not found with ID: %s",
                                            articleId)));

        // Update status to published and set published date
        article.setStatusCode("PUBLISHED");
        article.setPublishedAt(LocalDateTime.now());
        article.setUpdatedBy(publishedBy);
        article.setUpdatedAt(LocalDateTime.now());

        Article savedArticle = articleJpaRepository.save(article);
        log.info("Article published: {}", savedArticle.getId());

        return articleMapper.toArticleResponse(savedArticle);
    }

    public ArticleResponse unpublishArticle(@NonNull String articleId,
        @NonNull String unpublishedBy) {
        Article article =
            articleJpaRepository.findById(articleId)
                                .orElseThrow(
                                    () -> new RuntimeException(
                                        String.format(
                                            "Article not found with ID: %s",
                                            articleId)));

        // Update status to draft
        article.setStatusCode("DRAFT");
        article.setUpdatedBy(unpublishedBy);
        article.setUpdatedAt(LocalDateTime.now());

        Article savedArticle = articleJpaRepository.save(article);
        log.info("Article unpublished: {}", savedArticle.getId());

        return articleMapper.toArticleResponse(savedArticle);
    }
}
