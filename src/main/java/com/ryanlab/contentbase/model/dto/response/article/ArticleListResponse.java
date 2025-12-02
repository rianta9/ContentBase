package com.ryanlab.contentbase.model.dto.response.article;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleListResponse {
    private List<ArticleSummaryResponse> articles;
    private Long totalElements;
    private Integer totalPages;
    private Integer currentPage;
    private Integer pageSize;
    private Boolean hasNext;
    private Boolean hasPrevious;
}
