package com.ryanlab.contentbase.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ryanlab.contentbase.model.dto.response.user.account.AccountInfoResponse;
import com.ryanlab.contentbase.model.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

/**
 * Maps an Account entity to an AccountInfoResponse DTO.
 *
 * @param input the Account entity to be mapped
 * @return the mapped AccountInfoResponse DTO
 */
  @Mapping(source = "id", target = "userId")
  AccountInfoResponse toAccountInfoResponse(Account input);
}
