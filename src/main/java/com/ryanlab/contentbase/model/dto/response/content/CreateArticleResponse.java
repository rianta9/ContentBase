package com.ryanlab.contentbase.model.dto.response.content;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateArticleResponse {
  String articleId;
  String articleTitle;
  LocalDate createdDate;
}
