package com.ryanlab.contentbase.repository.jpa;

import org.springframework.stereotype.Repository;

import com.ryanlab.contentbase.core.repository.ICoreJpaRepository;
import com.ryanlab.contentbase.model.entity.ContentFavourite;

@Repository
public interface ContentFavouriteJpaRepository
  extends ICoreJpaRepository<ContentFavourite, String> {

  void deleteByUsernameAndContentId(String username, String contentId);

}
