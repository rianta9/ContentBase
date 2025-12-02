package com.ryanlab.contentbase.model.dto.response.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {
    private String id;
    private String categoryCode;
    private String categoryName;
    private String description;
}
