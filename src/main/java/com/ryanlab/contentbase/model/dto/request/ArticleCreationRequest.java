package com.ryanlab.contentbase.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArticleCreationRequest {
  @Size(min = 10, max = 255, message = "E_INVALID_INPUT_FIELD")
  String title;
  @NotBlank
  String content;
}
