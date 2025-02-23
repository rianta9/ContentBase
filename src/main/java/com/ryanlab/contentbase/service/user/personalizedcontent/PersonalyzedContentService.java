package com.ryanlab.contentbase.service.user.personalizedcontent;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.model.dto.response.content.ContentResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PersonalyzedContentService {
  public Page<ContentResponse> getPersonalyzedContents(String username) {
    // TODO Implement getPersonalyzedContents
    return null;
  }
}
