package com.ryanlab.contentbase.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ryanlab.contentbase.model.dto.response.user.account.AccountInfoResponse;
import com.ryanlab.contentbase.model.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
  @Mapping(source = "id.value", target = "userId")
  AccountInfoResponse toAccountInfoResponse(Account input);
}
