package com.ryanlab.contentbase.model.entity;

import org.springframework.util.Assert;

import com.ryanlab.contentbase.core.entity.CoreEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentCategory extends CoreEntity<ContentCategory> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Column
  String categoryId;
  @Column
  String parentsCategoryId;
  @Column
  String categoryName;

  public ContentCategory(
    String categoryId, String parentsCategoryId, String categoryName) {
    super();
    Assert.notNull(categoryId, "categoryId must be not null.");
    Assert.notNull(categoryName, "categoryName must be not null.");
    this.categoryId = categoryId;
    this.parentsCategoryId = parentsCategoryId;
    this.categoryName = categoryName;
  }

  public ContentCategory(
    String id, String categoryId, String parentsCategoryId,
    String categoryName) {
    super(id);
    Assert.notNull(categoryId, "categoryId must be not null.");
    Assert.notNull(categoryName, "categoryName must be not null.");
    this.categoryId = categoryId;
    this.parentsCategoryId = parentsCategoryId;
    this.categoryName = categoryName;
  }

}
