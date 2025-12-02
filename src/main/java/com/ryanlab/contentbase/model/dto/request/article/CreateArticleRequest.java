package com.ryanlab.contentbase.model.dto.request.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateArticleRequest {
    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 255, message = "Title must be between 5 and 255 characters")
    private String title;

    @NotBlank(message = "Slug is required")
    @Size(min = 3, max = 200, message = "Slug must be between 3 and 200 characters")
    private String slug;

    @NotBlank(message = "Content is required")
    @Size(min = 10, message = "Content must be at least 10 characters")
    private String content;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Size(max = 500, message = "Thumbnail URL must not exceed 500 characters")
    private String thumbnail;

    @Size(max = 50, message = "Status must not exceed 50 characters")
    private String status;
}
