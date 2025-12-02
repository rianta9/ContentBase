package com.ryanlab.contentbase.service.user.article;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.model.entity.Article;
import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DeleteOwnArticleService {
    ArticleJpaRepository articleJpaRepository;

    public void deleteOwnArticle(@NonNull String articleId,
        @NonNull String username) {
        Article article =
            articleJpaRepository.findById(articleId)
                                .orElseThrow(
                                    () -> new RuntimeException(
                                        String.format(
                                            "Article not found with ID: %s",
                                            articleId)));

        // Check ownership
        if (!article.getAuthorId().equals(username)) {
            throw new RuntimeException(
                "You are not authorized to delete this article");
        }

        articleJpaRepository.deleteById(articleId);
        log.info("User {} deleted their article: {}", username, articleId);
    }
}
