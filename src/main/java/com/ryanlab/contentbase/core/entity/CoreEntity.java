package com.ryanlab.contentbase.core.entity;


import org.springframework.util.Assert;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@MappedSuperclass
@Getter
@NoArgsConstructor
public class CoreEntity<E extends ICoreEntity<E>> implements ICoreEntity<E> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  public CoreEntity(String id) {
    Assert.notNull(id, "id must not be null");
    this.id = id;
  }

}
