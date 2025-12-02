package com.ryanlab.contentbase.model.entity;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

import com.ryanlab.contentbase.core.entity.CoreAuditedEntity;

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
@Table(name = "user_favourite", schema = "private")
public class UserFavourite extends CoreAuditedEntity<UserFavourite> {
  /**
   * 
   */
  static final long serialVersionUID = 1L;
  @Column
  String username;
  @Column
  String articleId;
  @Column
  Boolean favouriteStatus;

}
