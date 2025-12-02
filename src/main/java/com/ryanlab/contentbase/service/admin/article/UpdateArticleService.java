package com.ryanlab.contentbase.service.admin.article;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.model.dto.request.article.UpdateArticleRequest;
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
public class UpdateArticleService {
    ArticleJpaRepository articleJpaRepository;
    ArticleMapper articleMapper;

    public ArticleResponse updateArticle(@NonNull UpdateArticleRequest request,
        @NonNull String updatedBy) {
        Article article =
            articleJpaRepository.findById(request.getArticleId())
                                .orElseThrow(
                                    () -> new RuntimeException(
                                        String.format(
                                            "Article not found with ID: %s",
                                            request.getArticleId())));

        // Update fields if provided
        if (request.getTitle() != null) {
            article.setTitle(request.getTitle());
        }
        if (request.getSlug() != null) {
            article.setSlug(request.getSlug());
        }
        if (request.getSummary() != null) {
            article.setSummary(request.getSummary());
        }
        if (request.getContent() != null) {
            article.setContent(request.getContent());
        }
        if (request.getAvatarFileId() != null) {
            article.setAvatarFileId(request.getAvatarFileId());
        }
        if (request.getStatusCode() != null) {
            article.setStatusCode(request.getStatusCode());
        }

        // Update audit fields
        article.setUpdatedBy(updatedBy);
        article.setUpdatedAt(LocalDateTime.now());

        Article savedArticle = articleJpaRepository.save(article);
        log.info("Article updated: {}", savedArticle.getId());

        return articleMapper.toArticleResponse(savedArticle);
    }
}
