package com.ryanlab.contentbase.model.dto.response.article;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleLikeResponse {
    private String articleId;
    private Boolean isLiked;
    private Long likeCount;
}
