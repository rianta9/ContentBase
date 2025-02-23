package com.ryanlab.contentbase.repository.jpa;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ryanlab.contentbase.core.repository.ICoreJpaRepository;
import com.ryanlab.contentbase.model.entity.ContentStatistic;

@Repository
public interface ContentStatisticJpaRepository
  extends ICoreJpaRepository<ContentStatistic, String> {
  Optional<ContentStatistic> findOneByContentId(String contentId);
}
