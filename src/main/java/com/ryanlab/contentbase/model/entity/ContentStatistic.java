package com.ryanlab.contentbase.model.entity;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

import com.ryanlab.contentbase.core.entity.CoreEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Entity
@Accessors(fluent = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentStatistic extends CoreEntity<ContentStatistic> {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Column
  String contentId;
  @Column
  Long viewCount;
  @Column
  Long favouriteCount;
  @Column
  Long commentCount;
  @Column
  String createdBy;
  @Column
  String updatedBy;
  @Column
  LocalDateTime createdAt;
  @Column
  LocalDateTime updatedAt;

  @Builder
  public ContentStatistic(
    @NonNull String contentId, @NonNull Long viewCount,
    @NonNull Long favouriteCount, @NonNull Long commentCount,
    String createdBy, String updatedBy,
    LocalDateTime createdAt, LocalDateTime updatedAt) {
    super();
    this.contentId = contentId;
    this.viewCount = viewCount;
    this.favouriteCount = favouriteCount;
    this.commentCount = commentCount;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  @Builder
  public ContentStatistic(
    @NonNull String id, @NonNull String contentId, @NonNull Long viewCount,
    @NonNull Long favouriteCount, @NonNull Long commentCount,
    String createdBy, String updatedBy,
    LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id);
    this.contentId = contentId;
    this.viewCount = viewCount;
    this.favouriteCount = favouriteCount;
    this.commentCount = commentCount;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
