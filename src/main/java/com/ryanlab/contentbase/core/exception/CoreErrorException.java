//////////////////////////////////////////////////////
//
// * Code For Fun *_^
//
//////////////////////////////////////////////////////
package com.ryanlab.contentbase.core.exception;

import org.springframework.http.HttpStatus;

import com.ryanlab.contentbase.core.api.ResponseMessageId;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Core ErrorException
 * 
 * @project ContentBase
 * @author rianta9
 * @date_created Jan 6, 2025
 * @version 1.0
 * @see
 */
@Accessors(fluent = true)
@Getter
@Setter
public class CoreErrorException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /* Message */
  private String message;
  /** Status Code */
  private HttpStatus statusCode;
  /** API Message ID */
  private ResponseMessageId responseMessageId;
  /** Summary Args */
  private Object[] summaryArgs;
  /** Detail Args */
  private Object[] detailArgs;
  /** Cause */
  private Throwable cause;

  /**
   * CoreErrorException Constructor Important Parameters
   * 
   * @param message
   * @param statusCode
   * @param responseMessageId
   * @param cause
   */
  public CoreErrorException(
    String message, HttpStatus statusCode, ResponseMessageId responseMessageId,
    Throwable cause) {
    this.message = message;
    this.statusCode = statusCode;
    this.responseMessageId = responseMessageId;
    this.cause = cause;
  }

  /**
   * CoreErrorException Constructor Important Parameters
   * 
   * @param message
   * @param statusCode
   * @param responseMessageId
   */
  public CoreErrorException(
    String message, HttpStatus statusCode,
    ResponseMessageId responseMessageId) {
    this.message = message;
    this.statusCode = statusCode;
    this.responseMessageId = responseMessageId;
  }

  /**
   * CoreErrorException Constructor All Parameters
   * 
   * @param message
   * @param statusCode
   * @param responseMessageId
   * @param summaryArgs
   * @param detailArgs
   * @param cause
   */
  public CoreErrorException(
    String message, HttpStatus statusCode, ResponseMessageId responseMessageId,
    Object[] summaryArgs, Object[] detailArgs, Throwable cause) {
    this.message = message;
    this.statusCode = statusCode;
    this.responseMessageId = responseMessageId;
    this.summaryArgs = summaryArgs;
    this.detailArgs = detailArgs;
    this.cause = cause;
  }

  /**
   * CoreErrorException Constructor
   * 
   * @param message
   * @param statusCode
   * @param responseMessageId
   * @param summaryArgs
   * @param detailArgs
   */
  public CoreErrorException(
    String message, HttpStatus statusCode, ResponseMessageId responseMessageId,
    Object[] summaryArgs, Object[] detailArgs) {
    this.message = message;
    this.statusCode = statusCode;
    this.responseMessageId = responseMessageId;
    this.summaryArgs = summaryArgs;
    this.detailArgs = detailArgs;
  }
}
