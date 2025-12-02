package com.ryanlab.contentbase.repository.jpa;

import java.util.List;

import com.ryanlab.contentbase.core.repository.ICoreJpaRepository;
import com.ryanlab.contentbase.model.entity.ArticleCategory;

public interface ArticleCategoryJpaRepository
  extends ICoreJpaRepository<ArticleCategory, String> {
  List<ArticleCategory> findAllByArticleId(String articleId);

  void deleteAllByArticleId(String articleId);
}
