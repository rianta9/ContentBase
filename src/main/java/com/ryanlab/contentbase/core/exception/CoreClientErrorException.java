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
 * Core ClientErrorException
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
public class CoreClientErrorException extends CoreErrorException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * CoreClientErrorException Constructor Important Parameters
   * 
   * @param message
   * @param statusCode
   * @param responseMessageId
   * @param cause
   */
  public CoreClientErrorException(
    String message, HttpStatus statusCode, ResponseMessageId responseMessageId,
    Throwable cause) {
    super(message, statusCode, responseMessageId, cause);
  }

  /**
   * CoreClientErrorException Constructor All Parameters
   * 
   * @param message
   * @param statusCode
   * @param responseMessageId
   * @param summaryArgs
   * @param detailArgs
   * @param cause
   */
  public CoreClientErrorException(
    String message, HttpStatus statusCode, ResponseMessageId responseMessageId,
    Object[] summaryArgs, Object[] detailArgs, Throwable cause) {
    super(
      message,
      statusCode,
      responseMessageId,
      summaryArgs,
      detailArgs,
      cause);
  }

}
