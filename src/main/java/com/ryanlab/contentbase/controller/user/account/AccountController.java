package com.ryanlab.contentbase.controller.user.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryanlab.contentbase.model.dto.response.user.account.AccountInfoResponse;
import com.ryanlab.contentbase.service.user.account.GetAccountService;

import jakarta.websocket.server.PathParam;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/user/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
  
  final GetAccountService getAccountService;

  @GetMapping("/{userId}/get")
  public ResponseEntity<AccountInfoResponse>
    getUser(@PathParam(value = "userId") String userId) {
    AccountInfoResponse data = getAccountService.getAccount(userId);
    return ResponseEntity.ok().body(data);
  }

}
