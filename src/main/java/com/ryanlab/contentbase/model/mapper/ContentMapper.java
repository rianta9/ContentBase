package com.ryanlab.contentbase.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.ryanlab.contentbase.model.dto.request.ContentCreationRequest;
import com.ryanlab.contentbase.model.dto.request.ContentUpdateRequest;
import com.ryanlab.contentbase.model.dto.response.content.ContentResponse;
import com.ryanlab.contentbase.model.dto.response.content.CreateContentResponse;
import com.ryanlab.contentbase.model.entity.Content;

@Mapper(componentModel = "spring")
public interface ContentMapper {
  Content toContent(ContentCreationRequest input);

  void updateContent(@MappingTarget Content target, ContentUpdateRequest input);

  // @Mapping(source = "id.value", target = "id")
  CreateContentResponse toCreateContentResponse(Content input);

  ContentResponse toContentResponse(Content input);

}
