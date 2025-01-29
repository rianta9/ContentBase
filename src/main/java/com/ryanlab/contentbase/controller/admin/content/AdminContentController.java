package com.ryanlab.contentbase.controller.admin.content;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryanlab.contentbase.model.dto.request.ContentCreationRequest;
import com.ryanlab.contentbase.model.dto.response.content.CreateContentResponse;
import com.ryanlab.contentbase.service.admin.CreateContentService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/admin/content")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminContentController {

  CreateContentService createContentService;

  public ResponseEntity<String>
    createContent(@RequestBody @Valid ContentCreationRequest data) {
    CreateContentResponse response = createContentService.handleRequest(data);
    return ResponseEntity.ok(response.contentId());
  }
}
