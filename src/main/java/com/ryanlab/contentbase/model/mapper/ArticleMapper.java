package com.ryanlab.contentbase.model.mapper;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.ryanlab.contentbase.model.dto.response.content.ArticleResponse;
import com.ryanlab.contentbase.model.dto.response.content.CreateArticleResponse;
import com.ryanlab.contentbase.model.entity.Article;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
  ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

  @Mapping(source = "id", target = "contentId")
  @Mapping(source = "createdAt", target = "dateCreated", qualifiedByName = "localDateTimeToLocalDate")
  @Mapping(target = "categoryId", ignore = true)
  @Mapping(target = "categoryName", ignore = true)
  ArticleResponse toArticleResponse(Article article);

  CreateArticleResponse toCreateArticleResponse(Article article);

  /**
   * Convert LocalDateTime to LocalDate
   */
  @Named("localDateTimeToLocalDate")
  static LocalDate localDateTimeToLocalDate(java.time.LocalDateTime dateTime) {
    return dateTime != null ? dateTime.toLocalDate() : null;
  }
}
