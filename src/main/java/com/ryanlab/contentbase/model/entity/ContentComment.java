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
public class ContentComment extends CoreEntity<ContentComment> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column
  String contentId;
  @Column
  String parentsContentId;
  @Column
  String username;
  @Column
  String content;
  @Column
  String createdBy;
  @Column
  String updatedBy;
  @Column
  LocalDateTime createdAt;
  @Column
  LocalDateTime updatedAt;

  /**
     * Constructs a new ContentComment object.
     *
     * @param contentId      the ID of the content being commented on
     * @param parentsContentId the ID of the parent comment, if this is a reply
     * @param username       the username of the user who made the comment
     * @param content        the text content of the comment
     * @param createdBy      the username of the user who created the comment
     * @param updatedBy      the username of the user who last updated the comment
     * @param createdAt      the date and time the comment was created
     * @param updatedAt      the date and time the comment was last updated
     */
  @Builder
  public ContentComment(
    @NonNull String contentId, String parentsContentId,
    @NonNull String username, @NonNull String content,
    String createdBy, String updatedBy,
    LocalDateTime createdAt, LocalDateTime updatedAt) {
    super();
    this.contentId = contentId;
    this.parentsContentId = parentsContentId;
    this.username = username;
    this.content = content;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  /**
   * Constructs a new ContentComment instance.
   *
   * @param id               the unique identifier of the comment
   * @param contentId        the identifier of the content being commented on
   * @param parentsContentId the identifier of the parent comment (if any)
   * @param username         the username of the user who made the comment
   * @param content          the text content of the comment
   * @param createdBy        the user who created the comment
   * @param updatedBy        the user who last updated the comment
   * @param createdAt        the timestamp when the comment was created
   * @param updatedAt        the timestamp when the comment was last updated
   */
  @Builder
  public ContentComment(
    @NonNull String id, @NonNull String contentId, String parentsContentId,
    @NonNull String username, @NonNull String content,
    String createdBy,
    String updatedBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id);
    this.contentId = contentId;
    this.parentsContentId = parentsContentId;
    this.username = username;
    this.content = content;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

}
