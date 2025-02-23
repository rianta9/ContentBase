package com.ryanlab.contentbase.model.dto.request.contentcomment;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentCommentCreationRequest {
  @NotBlank
  String contentId;
  @NotBlank
  String content;
}
