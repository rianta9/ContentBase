package com.ryanlab.contentbase.core.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class MessageToUser {
  ResponseType responseType;
  int statusCode;
  String messageSummary;
  String messageDetail;
}
