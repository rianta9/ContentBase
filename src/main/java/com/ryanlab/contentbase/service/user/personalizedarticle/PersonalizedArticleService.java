package com.ryanlab.contentbase.service.user.personalizedarticle;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.model.dto.response.content.ArticleResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PersonalizedArticleService {
  public Page<ArticleResponse> getPersonalizedArticles(String username) {
    // TODO Implement getPersonalizedArticles
    return null;
  }
}
