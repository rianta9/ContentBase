package com.ryanlab.contentbase.service.user.contentcomment;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.core.exception.PermissionDeniedException;
import com.ryanlab.contentbase.core.exception.ResourceNotFoundException;
import com.ryanlab.contentbase.model.dto.request.contentcomment.ContentCommentCreationRequest;
import com.ryanlab.contentbase.model.entity.ContentComment;
import com.ryanlab.contentbase.repository.jpa.ContentCommentJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ContentCommentService {
  final ContentCommentJpaRepository contentCommentJpaRepository;

  public String addContentComment(ContentCommentCreationRequest request,
    @NonNull String username) {
    ContentComment contentComment =
      ContentComment.builder()
                    .contentId(request.contentId())
                    .username(username)
                    .content(request.content())
                    .createdBy(username)
                    .updatedBy(username)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
    contentCommentJpaRepository.save(contentComment);
    log.debug(
      String.format(
        "Added Content Comment: Id: %s, Username: %s",
        contentComment.id(),
        username));
    return contentComment.id();
  }

  public void deleteContentComment(@NonNull String id,
    @NonNull String username) {
    ContentComment contentComment =
      contentCommentJpaRepository.findById(id)
                                 .orElseThrow(
                                   () -> new ResourceNotFoundException(
                                     String.format(
                                       "Content Comment Not Found: %s",
                                       id),
                                     new Object[] { "Content Comment" },
                                     new Object[] {
                                       String.format("Id = %s", id) }));
    if (contentComment.username() != username) {
      throw new PermissionDeniedException(
        String.format(
          "User %s Have No Permission To Delete Content Comment: %s",
          username,
          id),
        new Object[] { "Content Comment" },
        new Object[] { String.format("Username = %s, Id = %s", username, id) });
    }
    contentCommentJpaRepository.delete(contentComment);
  }
}
