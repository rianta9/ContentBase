package com.ryanlab.contentbase.service.user.favourite;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.model.entity.UserFavourite;
import com.ryanlab.contentbase.repository.jpa.UserFavouriteJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserFavouriteService {
  final UserFavouriteJpaRepository userFavouriteJpaRepository;

  public String addUserFavourite(@NonNull String username,
    @NonNull String articleId) {
    UserFavourite userFavourite = UserFavourite.builder()
                                               .username(username)
                                               .articleId(articleId)
                                               .build();
    userFavourite.setCreatedBy(username);
    userFavourite.setUpdatedBy(username);
    userFavourite.setCreatedAt(LocalDateTime.now());
    userFavourite.setUpdatedAt(LocalDateTime.now());
    userFavouriteJpaRepository.save(userFavourite);
    log.debug(
      String.format(
        "Added User Favourite: Id: %s, Article Id: %s, Username: %s",
        userFavourite.getId(),
        articleId,
        username));
    return userFavourite.getId();
  }

  public void deleteUserFavourite(@NonNull String username,
    @NonNull String articleId) {
    userFavouriteJpaRepository.deleteByUsernameAndArticleId(
      username,
      articleId);
    log.debug(
      String.format(
        "Deleted User Favourite: Article Id: %s, Username: %s",
        articleId,
        username));
  }
}
