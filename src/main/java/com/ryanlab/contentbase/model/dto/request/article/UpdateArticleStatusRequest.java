package com.ryanlab.contentbase.model.dto.request.article;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateArticleStatusRequest {
  @NotBlank(message = "E_INVALID_INPUT_FIELD")
  String status; // DRAFT, PENDING, PUBLISHED, HIDDEN
}
