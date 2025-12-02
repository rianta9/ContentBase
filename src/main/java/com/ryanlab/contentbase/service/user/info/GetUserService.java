package com.ryanlab.contentbase.service.user.info;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.core.exception.ResourceNotFoundException;
import com.ryanlab.contentbase.model.dto.response.user.account.UserInfoResponse;
import com.ryanlab.contentbase.model.entity.User;
import com.ryanlab.contentbase.model.mapper.UserMapper;
import com.ryanlab.contentbase.repository.jpa.UserJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetUserService {
  final UserJpaRepository userJpaRepository;
  final UserMapper userMapper;

  public UserInfoResponse getUser(String userId) {
    Optional<User> data = userJpaRepository.findById(userId);
    if (data.isEmpty())
      throw new ResourceNotFoundException(
        String.format("User Not Found: %s", userId),
        new Object[] { "User" },
        new Object[] { String.format("UserId = %s", userId) });
    return userMapper.toUserInfoResponse(data.get());
  }
}
