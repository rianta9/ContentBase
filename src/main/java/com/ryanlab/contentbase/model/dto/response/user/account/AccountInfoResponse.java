package com.ryanlab.contentbase.model.dto.response.user.account;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Accessors(fluent = true)
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountInfoResponse {
  String userId;
  String userName;
  String fullName;
  LocalDate birthDay;
  String gender;
  String avatarUrl;
  String phone;
  String email;
  String address;
}
