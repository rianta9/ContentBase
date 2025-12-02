package com.ryanlab.contentbase.service.admin.article;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DeleteArticleService {
    ArticleJpaRepository articleJpaRepository;

    public void deleteArticle(@NonNull String articleId) {
        if (!articleJpaRepository.existsById(articleId)) {
            throw new RuntimeException(
                String.format("Article not found with ID: %s", articleId));
        }

        articleJpaRepository.deleteById(articleId);
        log.info("Article deleted: {}", articleId);
    }
}
