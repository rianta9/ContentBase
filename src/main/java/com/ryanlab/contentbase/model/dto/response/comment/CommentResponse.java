package com.ryanlab.contentbase.model.dto.response.comment;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponse {
    private String id;
    private String articleId;
    private String parentsCommentId;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
