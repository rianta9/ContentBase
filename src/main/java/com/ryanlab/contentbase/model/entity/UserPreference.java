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
public class UserPreference extends CoreEntity<UserPreference> {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Column
  String username;
  @Column
  String categoryId;
  @Column
  String createdBy;
  @Column
  String updatedBy;
  @Column
  LocalDateTime createdAt;
  @Column
  LocalDateTime updatedAt;

  @Builder
  public UserPreference(
    @NonNull String username, @NonNull String categoryId,
    String createdBy, String updatedBy,
    LocalDateTime createdAt, LocalDateTime updatedAt) {
    super();
    this.username = username;
    this.categoryId = categoryId;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  @Builder
  public UserPreference(
    @NonNull String id, @NonNull String username, @NonNull String categoryId,
    String createdBy,  String updatedBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id);
    this.username = username;
    this.categoryId = categoryId;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
