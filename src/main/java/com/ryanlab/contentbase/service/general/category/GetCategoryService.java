package com.ryanlab.contentbase.service.general.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.model.dto.response.category.CategoryResponse;
import com.ryanlab.contentbase.repository.jpa.CategoryJpaRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetCategoryService {
  final CategoryJpaRepository categoryJpaRepository;

  /**
   * Retrieves all categories.
   *
   * @return list of category responses
   */
  public List<CategoryResponse> getAllCategories() {
    return categoryJpaRepository.findAll()
                               .stream()
                               .map(this::toCategoryResponse)
                               .collect(Collectors.toList());
  }

  /**
   * Converts a Category entity to a CategoryResponse.
   *
   * @param category the category entity
   * @return the category response
   */
  private CategoryResponse toCategoryResponse(
    com.ryanlab.contentbase.model.entity.Category category) {
    return CategoryResponse.builder()
                          .id(category.getId())
                          .categoryCode(category.getCategoryCode())
                          .categoryName(category.getCategoryName())
                          .description(category.getDescription())
                          .build();
  }
}
