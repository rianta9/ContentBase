package com.ryanlab.contentbase.service.admin.article;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.model.dto.response.article.ArticleListResponse;
import com.ryanlab.contentbase.model.dto.response.article.ArticleSummaryResponse;
import com.ryanlab.contentbase.model.entity.Article;
import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetAllArticlesAdminService {
    ArticleJpaRepository articleJpaRepository;

    public ArticleListResponse getAllArticles(Integer page, Integer size,
        String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("ASC")
            ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Article> articlePage = articleJpaRepository.findAll(pageable);

        List<ArticleSummaryResponse> articles =
            articlePage.getContent()
                       .stream()
                       .map(this::toArticleSummary)
                       .collect(Collectors.toList());

        return ArticleListResponse.builder()
                                  .articles(articles)
                                  .totalElements(articlePage.getTotalElements())
                                  .totalPages(articlePage.getTotalPages())
                                  .currentPage(articlePage.getNumber())
                                  .pageSize(articlePage.getSize())
                                  .hasNext(articlePage.hasNext())
                                  .hasPrevious(articlePage.hasPrevious())
                                  .build();
    }

    private ArticleSummaryResponse toArticleSummary(Article article) {
        return ArticleSummaryResponse.builder()
                                     .id(article.getId())
                                     .articleId(article.getArticleId())
                                     .title(article.getTitle())
                                     .slug(article.getSlug())
                                     .summary(article.getSummary())
                                     .authorId(article.getAuthorId())
                                     .avatarFileId(article.getAvatarFileId())
                                     .statusCode(article.getStatusCode())
                                     .publishedAt(article.getPublishedAt())
                                     .createdAt(
                                         article.getAudit().getCreatedAt())
                                     .updatedAt(
                                         article.getAudit().getUpdatedAt())
                                     .build();
    }
}
