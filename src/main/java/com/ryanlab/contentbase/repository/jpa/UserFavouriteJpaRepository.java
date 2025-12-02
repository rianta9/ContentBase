package com.ryanlab.contentbase.repository.jpa;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.ryanlab.contentbase.core.repository.ICoreJpaRepository;
import com.ryanlab.contentbase.model.entity.UserFavourite;

@Repository
public interface UserFavouriteJpaRepository
  extends ICoreJpaRepository<UserFavourite, String> {
  UserFavourite findOneByArticleIdAndUsername(String articleId,
    String username);

  List<UserFavourite> findAllByArticleId(String articleId);

  void deleteByUsernameAndArticleId(String username, String articleId);
}
