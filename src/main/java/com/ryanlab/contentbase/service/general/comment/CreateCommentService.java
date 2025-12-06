package com.ryanlab.contentbase.service.general.comment;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.core.exception.ResourceNotFoundException;
import com.ryanlab.contentbase.core.util.RandomUUID;
import com.ryanlab.contentbase.model.dto.request.comment.CommentCreationRequest;
import com.ryanlab.contentbase.model.dto.response.comment.CommentResponse;
import com.ryanlab.contentbase.model.entity.Comment;
import com.ryanlab.contentbase.repository.jpa.ArticleJpaRepository;
import com.ryanlab.contentbase.repository.jpa.CommentJpaRepository;
import com.ryanlab.contentbase.service.general.articlestatistic.UpdateArticleStatisticService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateCommentService {
  final CommentJpaRepository commentJpaRepository;
  final ArticleJpaRepository articleJpaRepository;
  final UpdateArticleStatisticService updateArticleStatisticService;

  /**
   * Creates a comment on an article.
   *
   * @param articleId the article ID
   * @param request the comment creation request
   * @param username the username of the commenter
   * @return the created comment response
   * @throws ResourceNotFoundException if the article is not found
   */
  public CommentResponse createComment(
    String articleId, CommentCreationRequest request, String username) {
    // Verify article exists
    articleJpaRepository.findById(articleId)
                        .orElseThrow(
                          () -> new ResourceNotFoundException(
                            String.format("Article Not Found: %s", articleId),
                            new Object[] { "Article" },
                            new Object[] {
                              String.format("ArticleId = %s", articleId) }));

    Comment comment =
      Comment.builder()
             .id(RandomUUID.getRandomID())
             .articleId(articleId)
             .parentsCommentId(null) // Set to null for now, can be enhanced later
             .username(username)
             .content(request.getContent())
             .createdBy(username)
             .updatedBy(username)
             .createdAt(LocalDateTime.now())
             .updatedAt(LocalDateTime.now())
             .version(0)
             .build();

    Comment savedComment = commentJpaRepository.saveAndFlush(comment);

    // Update article statistic
    updateArticleStatisticService.incrementCommentCount(articleId);

    return toCommentResponse(savedComment);
  }

  /**
   * Converts a Comment entity to a CommentResponse.
   *
   * @param comment the comment entity
   * @return the comment response
   */
  private CommentResponse toCommentResponse(Comment comment) {
    return CommentResponse.builder()
                          .id(comment.getId())
                          .articleId(comment.getArticleId())
                          .parentsCommentId(comment.getParentsCommentId())
                          .username(comment.getUsername())
                          .content(comment.getContent())
                          .createdAt(comment.getCreatedAt())
                          .updatedAt(comment.getUpdatedAt())
                          .build();
  }
}
