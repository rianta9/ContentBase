package com.ryanlab.contentbase.core.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Accessors(fluent = true)
@Builder
public class ApiResponse {
  private int code;
  private String message;
  private Object body;
}
