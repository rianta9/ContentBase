package com.ryanlab.contentbase.model.dto.request.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateArticleRequest {
    @NotBlank(message = "Article ID is required")
    private String articleId;

    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @Size(max = 200, message = "Slug must not exceed 200 characters")
    private String slug;

    @Size(max = 500, message = "Summary must not exceed 500 characters")
    private String summary;

    private String content;

    private String avatarFileId;

    private String statusCode;
}
