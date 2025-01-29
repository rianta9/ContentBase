package com.ryanlab.contentbase.configuration.session;

import java.util.List;

import org.springframework.web.context.annotation.SessionScope;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@SessionScope
@Builder
@Accessors(fluent = true)
@Data
public class UserContext {
  private String username;
  private String fullName;
  private String avatarUrl;
  private List<String> roles;
}
