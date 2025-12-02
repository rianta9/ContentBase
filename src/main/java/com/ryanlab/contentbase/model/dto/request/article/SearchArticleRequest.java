package com.ryanlab.contentbase.model.dto.request.article;

import lombok.Data;

@Data
public class SearchArticleRequest {
    private String keyword;
    private String categoryCode;
    private String authorId;
    private String statusCode;
    private String sortBy = "createdAt"; // Default sort by creation date
    private String sortDirection = "DESC"; // Default descending
    private Integer page = 0;
    private Integer size = 20;
}
