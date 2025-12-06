package com.ryanlab.contentbase.configuration.exception_handling;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ryanlab.contentbase.core.api.ApiResponse;
import com.ryanlab.contentbase.core.api.MessagePattern;
import com.ryanlab.contentbase.core.api.MessageToUser;
import com.ryanlab.contentbase.core.api.ResponseMessageId;
import com.ryanlab.contentbase.core.api.ResponseType;
import com.ryanlab.contentbase.core.exception.ResourceNotFoundException;
import com.ryanlab.contentbase.model.entity.ResponseMessage;
import com.ryanlab.contentbase.repository.jpa.ResponseMessageJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * Class to handle exception to response to API request
 * 
 * @project ContentBase
 * @author rianta9
 * @date_created Jan 6, 2025
 * @version 1.0
 * @see
 */
@ControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GlobalExceptionHandler {
  ResponseMessageJpaRepository responseMessageJpaRepository;
  ResponseMessage DEFAULT_MESSAGE =
    ResponseMessage.builder()
                   .responseMessageId(
                     ResponseMessageId.E_INTERNAL_SERVER_ERROR.name())
                   .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                   .responseType(ResponseType.UNKNOW.name())
                   .summaryMessagePattern(
                     MessagePattern.builder()
                                   .value("Internal Server Error")
                                   .build())
                   .detailMessagePattern(
                     MessagePattern.builder()
                                   .value("Internal Server Error")
                                   .build())
                   .build();

  /**
   * Handle RuntimeException
   * 
   * @param exception
   * @return
   */
  @ExceptionHandler(value = RuntimeException.class)
  ResponseEntity<ApiResponse>
    handleRuntimeException(RuntimeException exception) {
    ResponseMessageId messageId =
      ResponseMessageId.toEnum(exception.getMessage());
    Object[] summaryArgs = {}, detailArgs = {};

    MessageToUser message = getMessage(messageId, summaryArgs, detailArgs);
    ApiResponse response =
      ApiResponse.builder()
                 .code(message.getStatusCode())
                 .body(getMessage(messageId, summaryArgs, detailArgs))
                 .build();
    return ResponseEntity.status(message.getStatusCode()).body(response);
  }

  /**
   * Handle MethodArgumentNotValidException
   * 
   * @param exception
   * @return
   */
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(
    MethodArgumentNotValidException exception) {
    ResponseMessageId messageId =
      ResponseMessageId.toEnum(exception.getMessage());
    Object[] summaryArgs = {}, detailArgs = {};
    MessageToUser message = getMessage(messageId, summaryArgs, detailArgs);
    ApiResponse response =
      ApiResponse.builder()
                 .code(message.getStatusCode())
                 .body(getMessage(messageId, summaryArgs, detailArgs))
                 .build();
    return ResponseEntity.status(message.getStatusCode()).body(response);
  }

  /**
   * Handle ResourceNotFoundException
   * 
   * @param exception
   * @return
   */
  @ExceptionHandler(value = ResourceNotFoundException.class)
  ResponseEntity<ApiResponse> handleResourceNotFoundException(
    ResourceNotFoundException exception) {
    log.warn("Resource not found: {}", exception.getMessage());
    ApiResponse response =
      ApiResponse.builder()
                 .code(HttpStatus.NOT_FOUND.value())
                 .body(
                   MessageToUser.builder()
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .messageSummary("Resource not found")
                                .messageDetail(exception.getMessage())
                                .build())
                 .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  private MessageToUser getMessage(ResponseMessageId messageId,
    Object[] summaryArgs, Object[] detailArgs) {
    Optional<ResponseMessage> query =
      responseMessageJpaRepository.findById(messageId.name());
    ResponseMessage message = DEFAULT_MESSAGE;
    if (query.isEmpty()) {
      log.error("Not found ResponseMessage with ID = " + messageId.name());
    } else {
      message = query.get();
    }
    return MessageToUser.builder()
                        .responseType(
                          ResponseType.toEnum(message.getResponseType()))
                        .statusCode(message.getStatusCode())
                        .messageSummary(
                          message.getSummaryMessagePattern()
                                 .toMessage(summaryArgs))
                        .messageDetail(
                          message.getDetailMessagePattern()
                                 .toMessage(detailArgs))
                        .build();

  }
}
