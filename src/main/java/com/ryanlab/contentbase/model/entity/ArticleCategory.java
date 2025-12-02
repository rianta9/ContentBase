package com.ryanlab.contentbase.model.entity;

import org.springframework.util.Assert;

import com.ryanlab.contentbase.core.entity.CoreEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "article_category", schema = "private")
public class ArticleCategory extends CoreEntity<ArticleCategory> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Column
  String articleId;
  @Column
  String categoryCode;

}
