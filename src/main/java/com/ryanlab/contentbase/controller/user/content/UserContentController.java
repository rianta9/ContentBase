package com.ryanlab.contentbase.controller.user.content;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ryanlab.contentbase.model.dto.request.contentcomment.ContentCommentCreationRequest;
import com.ryanlab.contentbase.model.dto.response.content.ContentResponse;
import com.ryanlab.contentbase.service.user.contentcomment.ContentCommentService;
import com.ryanlab.contentbase.service.user.contentfavourite.ContentFavouriteService;
import com.ryanlab.contentbase.service.user.personalizedcontent.PersonalyzedContentService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserContentController {
  final ContentFavouriteService contentFavouriteService;
  final ContentCommentService contentCommentService;
  final PersonalyzedContentService personalyzedContentService;

  @PostMapping("/user/content/favourite/{contentId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<String>
    addContentFavourite(@PathVariable String contentId, Authentication auth) {
    String username = auth.getName();
    contentFavouriteService.addContentFavourite(username, contentId);
    return ResponseEntity.ok("Added to favorites");
  }

  @DeleteMapping("/user/content/favourite/{contentId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<String> deleteContentFavourite(String contentId,
    Authentication auth) {
    String username = auth.getName();
    contentFavouriteService.deleteContentFavourite(username, contentId);
    return ResponseEntity.ok("Deleted favorite");
  }

  @PostMapping("/user/content/comment/{contentId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<String> addContentComment(String contentId,
    @RequestBody ContentCommentCreationRequest request, Authentication auth) {
    String username = auth.getName();
    contentCommentService.addContentComment(request, username);
    return ResponseEntity.ok("Deleted content favorite");
  }

  @DeleteMapping("/user/content/comment/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<String> deleteContentComment(@PathVariable String id,
    Authentication auth) {
    String username = auth.getName();
    contentCommentService.deleteContentComment(id, username);
    return ResponseEntity.ok("Deleted content comment");
  }

  @GetMapping("/user/content/personalized-contents")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Page<ContentResponse>>
    getPersonalizedContents(Authentication auth) {
    String username = auth.getName();
    Page<ContentResponse> response =
      personalyzedContentService.getPersonalyzedContents(username);
    return ResponseEntity.ok(response);
  }
}
