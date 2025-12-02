package com.ryanlab.contentbase.model.entity;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

import com.ryanlab.contentbase.core.entity.CoreAuditedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "comment", schema = "private")
public class Comment extends CoreAuditedEntity<Comment> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column
  String articleId;
  @Column
  String parentsCommentId;
  @Column
  String username;
  @Column
  String content;

  @Builder
  public Comment(
    @NonNull String articleId, String parentsCommentId,
    @NonNull String username, @NonNull String content, String createdBy,
    String updatedBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
    super();
    this.articleId = articleId;
    this.parentsCommentId = parentsCommentId;
    this.username = username;
    this.content = content;
  }

  @Builder
  public Comment(
    @NonNull String id, Integer version, @NonNull String articleId,
    String parentsCommentId, @NonNull String username, @NonNull String content,
    String createdBy, String updatedBy, LocalDateTime createdAt,
    LocalDateTime updatedAt) {
    super(id, version, createdBy, updatedBy, createdAt, updatedAt);
    this.articleId = articleId;
    this.parentsCommentId = parentsCommentId;
    this.username = username;
    this.content = content;
  }

}
