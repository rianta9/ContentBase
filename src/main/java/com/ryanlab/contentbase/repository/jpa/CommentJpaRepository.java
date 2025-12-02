package com.ryanlab.contentbase.repository.jpa;

import java.util.List;

import com.ryanlab.contentbase.core.repository.ICoreJpaRepository;
import com.ryanlab.contentbase.model.entity.Comment;

public interface CommentJpaRepository
  extends ICoreJpaRepository<Comment, String> {
  List<Comment> findAllByArticleId(String articleId);
}
