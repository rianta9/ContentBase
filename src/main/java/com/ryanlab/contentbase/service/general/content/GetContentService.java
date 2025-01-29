package com.ryanlab.contentbase.service.general.content;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.core.exception.ResourceNotFoundException;
import com.ryanlab.contentbase.model.dto.response.content.ContentResponse;
import com.ryanlab.contentbase.model.entity.Content;
import com.ryanlab.contentbase.model.mapper.ContentMapper;
import com.ryanlab.contentbase.repository.jpa.ContentJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetContentService {
  final ContentJpaRepository contentJpaService;
  final ContentMapper contentMapper;
  public ContentResponse getContent(String contentId) {
    Optional<Content> data = contentJpaService.findById(contentId);
    if (data.isEmpty())
      throw new ResourceNotFoundException(
        String.format("Content Not Found: %s", contentId),
        new Object[] { "Content" },
        new Object[] { String.format("ContentId = %s", contentId) });
    return contentMapper.toContentResponse(data.get());
  }

}
