package com.ryanlab.contentbase.model.entity;


import com.ryanlab.contentbase.core.api.MessagePattern;
import com.ryanlab.contentbase.core.entity.CoreEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Accessors(fluent = true)
@Getter
public class ResponseMessage extends CoreEntity<ResponseMessage> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(nullable = false)
  String responseMessageId;
  @Column(nullable = false)
  String responseType;
  @Column(nullable = false)
  int statusCode;
  @Column(nullable = false)
  @AttributeOverride(name = "value", column = @Column(name = "summary_message_pattern"))
  MessagePattern summaryMessagePattern;
  @Column
  @AttributeOverride(name = "value", column = @Column(name = "detail_message_pattern"))
  MessagePattern detailMessagePattern;

}
