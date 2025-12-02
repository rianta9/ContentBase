package com.ryanlab.contentbase.model.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentCreationRequest {
  @NotBlank
  String articleId;
  @NotBlank
  String content;
}
