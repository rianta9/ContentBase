package com.ryanlab.contentbase.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ryanlab.contentbase.model.dto.response.content.ArticleResponse;
import com.ryanlab.contentbase.model.dto.response.content.CreateArticleResponse;
import com.ryanlab.contentbase.model.entity.Article;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
  ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

  @Mapping(source = "id", target = "contentId")
  ArticleResponse toArticleResponse(Article article);

  CreateArticleResponse toCreateArticleResponse(Article article);
}
