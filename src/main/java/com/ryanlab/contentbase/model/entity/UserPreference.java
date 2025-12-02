package com.ryanlab.contentbase.model.entity;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

import com.ryanlab.contentbase.core.entity.CoreEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

}
