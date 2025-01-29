package com.ryanlab.contentbase.core.entity;

import java.time.LocalDateTime;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

import com.ryanlab.contentbase.core.util.DateTimeUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A core audited entity class
 * 
 * @project ContentBase
 * @author rianta9
 * @date_created Jan 4, 2025
 * @version 1.0
 * @see
 */
@Embeddable
@Accessors(fluent = true)
@Audited
@Builder(toBuilder = true)
@NoArgsConstructor
@Getter
public class CoreAudit implements ICoreEntityAudit {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @CreatedDate
  @Column
  private LocalDateTime createdAt;

  @CreatedBy
  @Column(columnDefinition = "nvarchar(255)")
  private String createdBy;

  @LastModifiedDate
  @Column
  private LocalDateTime updatedAt;

  @LastModifiedBy
  @Column(columnDefinition = "nvarchar(255)")
  private String updatedBy;

  public CoreAudit(
    LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt,
    String updatedBy) {
    Assert.notNull(createdAt, () -> "createdAt must not be null.");
    Assert.notNull(createdBy, () -> "createdBy must not be null.");
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.updatedAt = updatedAt;
    this.updatedBy = updatedBy;
  }

  public String getStringCreatedAt() {
    return DateTimeUtils.toStringType(createdAt);
  }

  public String getStringUpdatedAt() {
    return DateTimeUtils.toStringType(updatedAt);
  }

}
