package com.ryanlab.contentbase.model.entity;

import java.time.LocalDateTime;

import com.ryanlab.contentbase.core.entity.CoreEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Content extends CoreEntity<Content> {
  /**
   * 
   */
  static final long serialVersionUID = 1L;
  @Column
  String title;
  @Column
  String content;
  @Column
  String createdBy;
  @Column
  String updatedBy;
  @Column
  LocalDateTime createdDate;
  @Column
  LocalDateTime updatedDate;

  @Builder
  public Content(
    String title, String content, String createdBy, String updatedBy,
    LocalDateTime createdDate, LocalDateTime updatedDate) {
    super();
    this.title = title;
    this.content = content;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }

  @Builder
  public Content(
    String id, String title, String content, String createdBy, String updatedBy,
    LocalDateTime createdDate, LocalDateTime updatedDate) {
    super(id);
    this.title = title;
    this.content = content;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }
}
