package com.ryanlab.contentbase.model.dto.response.content;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Accessors(fluent = true)
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentResponse {
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
