package com.ryanlab.contentbase.model.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;

import com.ryanlab.contentbase.core.entity.CoreEntity;
import com.ryanlab.contentbase.core.enums.DisplayStatusEnum;
import com.ryanlab.contentbase.core.enums.RoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "users", schema = "private")
public class User extends CoreEntity<User> {
  /**
   * 
   */
  static final long serialVersionUID = 1L;
  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String fullName;

  @Column
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthday;

  @Column
  private String gender;

  @Column(unique = true)
  private String phone;

  @Column(nullable = false, unique = true)
  private String email;

  @Column
  private String address;

  @Column
  private String avatarFileId;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private RoleEnum roleCode;

  @Column(nullable = false)
  @ColumnDefault("1")
  @Enumerated(EnumType.STRING)
  private DisplayStatusEnum status;

  /**
   * Constructor User
   * 
   * @param version
   * @param audit
   * @param username
   * @param password
   * @param fullName
   * @param birthDay
   * @param gender
   * @param phone
  /**
   * Constructor User
   */
}
