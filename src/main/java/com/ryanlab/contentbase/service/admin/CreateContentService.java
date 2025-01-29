package com.ryanlab.contentbase.service.admin;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.configuration.session.UserContext;
import com.ryanlab.contentbase.core.util.RandomUUID;
import com.ryanlab.contentbase.model.dto.request.ContentCreationRequest;
import com.ryanlab.contentbase.model.dto.response.content.CreateContentResponse;
import com.ryanlab.contentbase.model.entity.Content;
import com.ryanlab.contentbase.repository.jpa.ContentJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateContentService {
  ContentJpaRepository contentJpaRepository;
  UserContext userContext;

  public CreateContentResponse handleRequest(ContentCreationRequest input) {
    Content content =
      Content.builder()
             .id(RandomUUID.getRandomID())
             .title(input.title())
             .content(input.content())
             .createdBy(userContext.username())
             .updatedBy(userContext.username())
             .createdDate(LocalDateTime.now())
             .updatedDate(LocalDateTime.now())
             .build();
    Content result = contentJpaRepository.saveAndFlush(content);
    return CreateContentResponse.builder()
                                .contentId(result.id())
                                .build();
  }

}
