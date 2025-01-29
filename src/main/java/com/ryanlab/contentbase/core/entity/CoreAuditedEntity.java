package com.ryanlab.contentbase.core.entity;



import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A core audited entity class
 * 
 * @project ContentBase
 * @author rianta9
 * @date_created Jan 4, 2025
 * @version 1.0
 * @see
 */
@Accessors(fluent = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class CoreAuditedEntity<E extends ICoreEntity<E>> extends CoreEntity<E> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column
  @Version
  private Integer version = 0;

  @Embedded
  private CoreAudit audit;

  /**
   * Constructor of CoreAditedEntity
   * 
   * @param id
   * @param version
   * @param audit
   */
  public CoreAuditedEntity(String id, Integer version, CoreAudit audit) {
    super(id);
    Assert.notNull(version, () -> "version must not be null.");
    Assert.notNull(audit, () -> "audit must not be null.");
    this.version = version;
    this.audit = audit;
  }

}
