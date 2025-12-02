package com.ryanlab.contentbase.model.entity;

import java.time.LocalDateTime;

import com.ryanlab.contentbase.core.entity.CoreAudit;
import com.ryanlab.contentbase.core.entity.CoreAuditedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "article", schema = "private")
public class Article extends CoreAuditedEntity<Article> {
  /**
   * 
   */
  static final long serialVersionUID = 1L;
  
  @Column(unique = true)
  String articleId;
  
  @Column
  String title;
  
  @Column(unique = true)
  String slug;
  
  @Column
  String summary;
  
  @Column
  String content;
  
  @Column
  String authorId;
  
  @Column
  String avatarFileId;
  
  @Column
  String statusCode;
  
  @Column
  LocalDateTime publishedAt;

}
