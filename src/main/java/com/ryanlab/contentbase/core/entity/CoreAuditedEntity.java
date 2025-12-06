package com.ryanlab.contentbase.core.entity;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A core audited entity class
 * 
 * @project ContentBase
 * @author rianta9
 * @date_created Jan 4, 2025
 * @version 1.0
 * @see
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class CoreAuditedEntity<E extends ICoreEntity<E>> extends CoreEntity<E> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column
  @Version
  private Integer version = 0;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  // @Embedded - Audit fields are now mapped as separate columns above
  // private CoreAudit audit;

  /**
   * Constructor of CoreAditedEntity
   * 
   * @param id
   * @param version
   * @param audit
   */
  public CoreAuditedEntity(String id, Integer version, CoreAudit audit) {
    super(id);
    if (audit != null) {
      this.version = version != null ? version : 0;
      this.createdBy = audit.getCreatedBy();
      this.updatedBy = audit.getUpdatedBy();
      this.createdAt = audit.getCreatedAt();
      this.updatedAt = audit.getUpdatedAt();
    }
  }

  /**
   * Constructor of CoreAuditedEntity
   * 
   * @param id
   * @param version
   * @param createdBy
   * @param updatedBy
   * @param createdAt
   * @param updatedAt
   */
  public CoreAuditedEntity(
    String id, Integer version, String createdBy, String updatedBy,
    LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id);
    this.version = version;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

}
