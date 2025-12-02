package com.ryanlab.contentbase.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ryanlab.contentbase.model.dto.response.user.account.UserInfoResponse;
import com.ryanlab.contentbase.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  /**
   * Maps a User entity to a UserInfoResponse DTO.
   *
   * @param user the User entity to be mapped
   * @return the mapped UserInfoResponse DTO
   */
  @Mapping(source = "id", target = "userId")
  UserInfoResponse toUserInfoResponse(User user);
}
