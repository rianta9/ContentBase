package com.ryanlab.contentbase.core.exception;

import org.springframework.http.HttpStatus;

import com.ryanlab.contentbase.core.api.ResponseMessageId;

public class PermissionDeniedException extends CoreErrorException {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public PermissionDeniedException(
    String message,
    HttpStatus statusCode, ResponseMessageId responseMessageId,
    Object[] summaryArgs, Object[] detailArgs, Throwable cause) {
    super(
      message,
      statusCode,
      responseMessageId,
      summaryArgs,
      detailArgs,
      cause);
  }

  public PermissionDeniedException(
    String message,
    HttpStatus statusCode, ResponseMessageId responseMessageId,
    Object[] summaryArgs, Object[] detailArgs) {
    super(message, statusCode, responseMessageId, summaryArgs, detailArgs);
  }

  public PermissionDeniedException(
    String message,
    Object[] summaryArgs,
    Object[] detailArgs) {
    super(
      message,
      HttpStatus.NOT_FOUND,
      ResponseMessageId.E_RESOURCE_NOT_FOUND,
      summaryArgs,
      detailArgs);
  }

  public PermissionDeniedException(
    String message,
    Object[] summaryArgs,
    Object[] detailArgs, Throwable cause) {
    super(
      message,
      HttpStatus.NOT_FOUND,
      ResponseMessageId.E_RESOURCE_NOT_FOUND,
      summaryArgs,
      detailArgs,
      cause);
  }
}
