package com.ryanlab.contentbase.controller.general;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryanlab.contentbase.model.dto.response.content.ContentResponse;
import com.ryanlab.contentbase.service.general.content.GetContentService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/general/content")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContentController {
  GetContentService getContentService;

  @GetMapping(value = "/{contentId}/get", produces = "application/json")
  public ResponseEntity<ContentResponse> getContent(
    @PathVariable(required = true) @Valid String contentId) {
    System.out.println("getContent:" + contentId);
    ContentResponse result = getContentService.getContent(contentId);
    return ResponseEntity.ok()
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(result);
  }

}
