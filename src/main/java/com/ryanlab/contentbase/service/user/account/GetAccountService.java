package com.ryanlab.contentbase.service.user.account;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.core.exception.ResourceNotFoundException;
import com.ryanlab.contentbase.model.dto.response.user.account.AccountInfoResponse;
import com.ryanlab.contentbase.model.entity.Account;
import com.ryanlab.contentbase.model.mapper.AccountMapper;
import com.ryanlab.contentbase.repository.jpa.AccountJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetAccountService {
  final AccountJpaRepository accountJpaRepository;
  final AccountMapper accountMapper;
  public AccountInfoResponse getAccount(String userId) {
    Optional<Account> data =
      accountJpaRepository.findById(userId);
    if (data.isEmpty())
      throw new ResourceNotFoundException(
        String.format("User Not Found: %s", userId),
        new Object[] { "Account" },
        new Object[] { String.format("UserId = %s", userId) });
    return accountMapper.toAccountInfoResponse(data.get());
  }
}
