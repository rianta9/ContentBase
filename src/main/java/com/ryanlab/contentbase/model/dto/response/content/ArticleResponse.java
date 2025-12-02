package com.ryanlab.contentbase.model.dto.response.content;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArticleResponse {
  @JsonProperty
  String contentId;
  @JsonProperty
  String title;
  @JsonProperty
  LocalDate dateCreated;
  @JsonProperty
  String content;
  @JsonProperty
  String categoryId;
  @JsonProperty
  String categoryName;
}
