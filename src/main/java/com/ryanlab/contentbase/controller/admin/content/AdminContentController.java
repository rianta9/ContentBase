package com.ryanlab.contentbase.controller.admin.content;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ryanlab.contentbase.model.dto.request.ContentCreationRequest;
import com.ryanlab.contentbase.model.dto.response.content.CreateContentResponse;
import com.ryanlab.contentbase.service.admin.CreateContentService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminContentController {

  CreateContentService createContentService;

  @PostMapping
  public ResponseEntity<String>
    createContent(@RequestBody @Valid ContentCreationRequest data) {
    CreateContentResponse response = createContentService.handleRequest(data);
    return ResponseEntity.ok(response.contentId());
  }
  
  @PutMapping("/admin/content/{contentId}")
  public String updateContent(String contentId, Object data) {
    return null;
  }

  @DeleteMapping("/admin/content/{contentId}")
  public String deleteContent(String contentId) {
    return null;
  }

  @GetMapping("/admin/content/analytics")
  public String getContentAnalytic(String contentId) {
    return null;
  }
}
