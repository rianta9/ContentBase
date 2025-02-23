package com.ryanlab.contentbase.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ryanlab.contentbase.core.repository.ICoreJpaRepository;
import com.ryanlab.contentbase.model.entity.Content;

@Repository
public interface ContentJpaRepository
  extends ICoreJpaRepository<Content, String> {

  Page<Content> getPublicContents(String category, Pageable pageable);

  List<Content> getTopContent(String category, String time,
    int tOP_CONTENTS_SIZE);

  Optional<Content> findByContentId(String contentId);

  Page<Content> searchContents(String keyword, Pageable pageable);

}
