package com.ryanlab.contentbase.model.entity;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

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
public class ContentFavourite extends CoreEntity<ContentFavourite> {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Column
  String contentId;
  @Column
  String username;
  @Column
  String createdBy;
  @Column
  String updatedBy;
  @Column
  LocalDateTime createdAt;
  @Column
  LocalDateTime updatedAt;

  @Builder
  public ContentFavourite(
    @NonNull String contentId, @NonNull String username, String createdBy,
    String updatedBy,
    LocalDateTime createdAt, LocalDateTime updatedAt) {
    super();
    this.contentId = contentId;
    this.username = username;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  @Builder
  public ContentFavourite(
    @NonNull String id, @NonNull String contentId, @NonNull String username,
    String createdBy, String updatedBy,
    LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id);
    this.contentId = contentId;
    this.username = username;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

}
