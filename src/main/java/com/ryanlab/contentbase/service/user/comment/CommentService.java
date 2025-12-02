package com.ryanlab.contentbase.service.user.comment;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.core.exception.PermissionDeniedException;
import com.ryanlab.contentbase.core.exception.ResourceNotFoundException;
import com.ryanlab.contentbase.model.dto.request.comment.CommentCreationRequest;
import com.ryanlab.contentbase.model.entity.Comment;
import com.ryanlab.contentbase.repository.jpa.CommentJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentService {
  final CommentJpaRepository commentJpaRepository;

  public String addComment(CommentCreationRequest request,
    @NonNull String username) {
    Comment comment = Comment.builder()
                             .articleId(request.getArticleId())
                             .username(username)
                             .content(request.getContent())
                             .createdBy(username)
                             .updatedBy(username)
                             .createdAt(LocalDateTime.now())
                             .updatedAt(LocalDateTime.now())
                             .build();
    commentJpaRepository.save(comment);
    log.debug(
      String.format(
        "Added Comment: Id: %s, Username: %s",
        comment.getId(),
        username));
    return comment.getId();
  }

  public void deleteComment(@NonNull String id, @NonNull String username) {
    Comment comment =
      commentJpaRepository.findById(id)
                          .orElseThrow(
                            () -> new ResourceNotFoundException(
                              String.format("Comment Not Found: %s", id),
                              new Object[] { "Comment" },
                              new Object[] { String.format("Id = %s", id) }));
    if (comment.getUsername() != username) {
      throw new PermissionDeniedException(
        String.format(
          "User %s Have No Permission To Delete Comment: %s",
          username,
          id),
        new Object[] { "Comment" },
        new Object[] { String.format("Username = %s, Id = %s", username, id) });
    }
    commentJpaRepository.delete(comment);
  }
}
