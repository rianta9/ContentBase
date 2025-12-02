package com.ryanlab.contentbase.test.fixture;

import java.time.LocalDateTime;

import com.ryanlab.contentbase.core.util.RandomUUID;
import com.ryanlab.contentbase.model.entity.Category;

public class CategoryTestFixture {

  public static Category.CategoryBuilder createCategory(String code, String name) {
    return Category.builder()
                   .id(RandomUUID.getRandomID())
                   .categoryCode(code)
                   .categoryName(name)
                   .description("Test category: " + name)
                   .version(0)
                   .createdBy("test-user")
                   .updatedBy("test-user")
                   .createdAt(LocalDateTime.now())
                   .updatedAt(LocalDateTime.now());
  }

  public static Category.CategoryBuilder createTechCategory() {
    return createCategory("TECH", "Technology");
  }

  public static Category.CategoryBuilder createSportsCategory() {
    return createCategory("SPORTS", "Sports");
  }

  public static Category.CategoryBuilder createBusinessCategory() {
    return createCategory("BUSINESS", "Business");
  }
}
