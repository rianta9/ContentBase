package com.ryanlab.contentbase.model.dto.response.user.account;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoResponse {
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
