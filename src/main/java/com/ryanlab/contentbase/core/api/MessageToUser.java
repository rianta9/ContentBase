package com.ryanlab.contentbase.core.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class MessageToUser {
  ResponseType responseType;
  int statusCode;
  String messageSummary;
  String messageDetail;
}
