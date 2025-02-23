package com.ryanlab.contentbase.controller.general;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ryanlab.contentbase.model.dto.response.content.ContentResponse;
import com.ryanlab.contentbase.service.general.content.GetContentService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContentController {
  GetContentService getContentService;

  @GetMapping(value = "/general/content/{contentId}", produces = "application/json")
  public ResponseEntity<ContentResponse> getContent(
    @PathVariable(required = true) @Valid String contentId) {
    System.out.println("getContent:" + contentId);
    ContentResponse response = getContentService.getContent(contentId);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/general/content/list")
  public ResponseEntity<Page<ContentResponse>> getContents(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(required = false) String category,
    @RequestParam(defaultValue = "created_at,desc") String sort) {
    Page<ContentResponse> response =
      getContentService.getContents(page, size, category, sort);
    return ResponseEntity.ok(response);
  }
  
  @GetMapping("/general/content/top")
  public ResponseEntity<List<ContentResponse>> getTopContents(
    @RequestParam(required = false) String category,
    @RequestParam(defaultValue = "day") String time) {
    List<ContentResponse> response =
      getContentService.getTopContents(category, time);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/general/content/search")
  public ResponseEntity<Page<ContentResponse>> searchContents(
    @RequestParam String keyword, @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
    Page<ContentResponse> response =
      getContentService.search(keyword, page, size);
    return ResponseEntity.ok(response);
  }

}
