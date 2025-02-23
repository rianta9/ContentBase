package com.ryanlab.contentbase.service.user.contentfavourite;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.model.entity.ContentFavourite;
import com.ryanlab.contentbase.repository.jpa.ContentFavouriteJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ContentFavouriteService {
  final ContentFavouriteJpaRepository contentFavouriteJpaRepository;

  public String addContentFavourite(@NonNull String username,
    @NonNull String contentId) {
    ContentFavourite contentFavourite =
      ContentFavourite.builder()
                      .username(username)
                      .contentId(contentId)
                      .createdBy(username)
                      .updatedBy(username)
                      .createdAt(LocalDateTime.now())
                      .updatedAt(LocalDateTime.now())
                      .build();
    contentFavouriteJpaRepository.save(contentFavourite);
    log.debug(
      String.format(
        "Added Content Favourite: Id: %s, Content Id: %s, Username: %s",
        contentFavourite.id(),
        contentId,
        username));
    return contentFavourite.id();
  }

  public void deleteContentFavourite(@NonNull String username,
    @NonNull String contentId) {
    contentFavouriteJpaRepository.deleteByUsernameAndContentId(
      username,
      contentId);
    log.debug(
      String.format(
        "Deleted Content Favourite: Content Id: %s, Username: %s",
        contentId,
        username));
  }
}
