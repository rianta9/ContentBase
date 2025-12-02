package com.ryanlab.contentbase.model.dto.response.article;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleSummaryResponse {
    private String id;
    private String articleId;
    private String title;
    private String slug;
    private String summary;
    private String authorId;
    private String avatarFileId;
    private String statusCode;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long viewCount;
    private Long likeCount;
    private Long commentCount;
}
