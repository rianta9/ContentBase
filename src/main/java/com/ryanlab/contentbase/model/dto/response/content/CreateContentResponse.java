package com.ryanlab.contentbase.model.dto.response.content;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Accessors(fluent = true)
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateContentResponse {
  String contentId;
  String contentTitle;
  LocalDate createdDate;
}
