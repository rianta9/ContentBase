package com.ryanlab.contentbase.model.entity;

import org.hibernate.envers.Audited;

import com.ryanlab.contentbase.core.entity.CoreAuditedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "category", schema = "private")
@Builder(toBuilder = true)
@Audited
public class Category extends CoreAuditedEntity<Category> {
  /**
   * 
   */
  static final long serialVersionUID = 1L;

  @Column(unique = true)
  String categoryCode;

  @Column(unique = true)
  String categoryName;

  @Column(columnDefinition = "text")
  String description;

  /**
   * Constructor of Category
   */
  public Category() {
    super();
  }

  /**
   * Constructor of Category
   * 
   * @param categoryCode
   * @param categoryName
   * @param description
   */
  @Builder
  public Category(String categoryCode, String categoryName, String description) {
    super();
    this.categoryCode = categoryCode;
    this.categoryName = categoryName;
    this.description = description;
  }

  /**
   * Constructor of Category
   * 
   * @param id
   * @param categoryCode
   * @param categoryName
   * @param description
   * @param version
   * @param createdBy
   * @param updatedBy
   * @param createdAt
   * @param updatedAt
   */
  @Builder
  public Category(
    String id, String categoryCode, String categoryName, String description,
    Integer version, String createdBy, String updatedBy,
    java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt) {
    super(id, version, createdBy, updatedBy, createdAt, updatedAt);
    this.categoryCode = categoryCode;
    this.categoryName = categoryName;
    this.description = description;
  }
}
