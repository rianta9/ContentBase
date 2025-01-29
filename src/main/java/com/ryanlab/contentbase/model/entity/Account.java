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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends CoreEntity<Account> {
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
   * Constructor Account
   * 
   * @param version
   * @param audit
   * @param username
   * @param password
   * @param fullName
   * @param birthDay
   * @param gender
   * @param phone
   * @param email
   * @param address
   * @param avatarFileUrl
   * @param roleCode
   * @param status
   */
  @Builder
  public Account(
    String username, String password, String fullName, LocalDate birthday,
    String gender, String phone, String email, String address,
    String avatarFileUrl, RoleEnum roleCode, DisplayStatusEnum status) {
    super();
    Assert.notNull(username, () -> "username must not be null.");
    Assert.notNull(password, () -> "password must not be null.");
    Assert.notNull(fullName, () -> "fullName must not be null.");
    Assert.notNull(roleCode, () -> "roleCode must not be null.");
    Assert.notNull(status, () -> "status must not be null.");
    this.username = username;
    this.password = password;
    this.fullName = fullName;
    this.birthday = birthday;
    this.gender = gender;
    this.phone = phone;
    this.email = email;
    this.address = address;
    this.avatarFileId = avatarFileUrl;
    this.roleCode = roleCode;
    this.status = status;
  }

  /**
   * Constructor Account
   * 
   * @param id
   * @param version
   * @param audit
   * @param username
   * @param password
   * @param fullName
   * @param birthDay
   * @param gender
   * @param phone
   * @param email
   * @param address
   * @param avatarFileUrl
   * @param roleCode
   * @param status
   */
  @Builder
  public Account(
    String id, String username,
    String password, String fullName, LocalDate birthday, String gender,
    String phone, String email, String address, String avatarFileUrl,
    RoleEnum roleCode, DisplayStatusEnum status) {
    super(id);
    Assert.notNull(username, () -> "username must not be null.");
    Assert.notNull(password, () -> "password must not be null.");
    Assert.notNull(fullName, () -> "fullName must not be null.");
    Assert.notNull(roleCode, () -> "roleCode must not be null.");
    Assert.notNull(status, () -> "status must not be null.");
    this.username = username;
    this.password = password;
    this.fullName = fullName;
    this.birthday = birthday;
    this.gender = gender;
    this.phone = phone;
    this.email = email;
    this.address = address;
    this.avatarFileId = avatarFileUrl;
    this.roleCode = roleCode;
    this.status = status;
  }
}
