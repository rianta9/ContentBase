package com.ryanlab.contentbase.controller.user.info;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryanlab.contentbase.model.dto.response.user.account.UserInfoResponse;
import com.ryanlab.contentbase.service.user.info.GetUserService;

import jakarta.websocket.server.PathParam;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/user/info")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

  final GetUserService getUserService;

  @GetMapping("/{userId}/get")
  public ResponseEntity<UserInfoResponse>
    getUser(@PathParam(value = "userId") String userId) {
    UserInfoResponse data = getUserService.getUser(userId);
    return ResponseEntity.ok().body(data);
  }

}
