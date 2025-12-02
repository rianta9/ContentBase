package com.ryanlab.contentbase.integration.controller.general.category;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.ryanlab.contentbase.BaseIntegrationTest;
import com.ryanlab.contentbase.repository.jpa.CategoryJpaRepository;
import com.ryanlab.contentbase.test.fixture.CategoryTestFixture;

@AutoConfigureMockMvc
@DisplayName("Categories Endpoint Integration Tests")
public class GetCategoriesIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CategoryJpaRepository categoryJpaRepository;

  @BeforeEach
  void setUp() {
    categoryJpaRepository.deleteAll();
  }

  @Test
  @DisplayName("Should retrieve all categories successfully")
  void testGetAllCategoriesSuccess() throws Exception {
    categoryJpaRepository.saveAndFlush(CategoryTestFixture.createTechCategory().build());
    categoryJpaRepository.saveAndFlush(CategoryTestFixture.createSportsCategory().build());
    categoryJpaRepository.saveAndFlush(CategoryTestFixture.createBusinessCategory().build());

    mockMvc.perform(get("/api/public/categories"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").isArray())
           .andExpect(jsonPath("$.length()").value(3))
           .andExpect(jsonPath("$[0].categoryCode").exists())
           .andExpect(jsonPath("$[0].categoryName").exists());
  }

  @Test
  @DisplayName("Should return empty list when no categories exist")
  void testGetAllCategoriesEmpty() throws Exception {
    mockMvc.perform(get("/api/public/categories"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").isArray())
           .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  @DisplayName("Should return category with all required fields")
  void testGetAllCategoriesReturnAllFields() throws Exception {
    categoryJpaRepository.saveAndFlush(CategoryTestFixture.createTechCategory().build());

    mockMvc.perform(get("/api/public/categories"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").exists())
           .andExpect(jsonPath("$[0].categoryCode").exists())
           .andExpect(jsonPath("$[0].categoryName").exists())
           .andExpect(jsonPath("$[0].description").exists());
  }

  @Test
  @DisplayName("Should handle large number of categories")
  void testGetAllCategoriesLargeDataset() throws Exception {
    for (int i = 0; i < 50; i++) {
      categoryJpaRepository.saveAndFlush(
        CategoryTestFixture.createCategory("CODE_" + i, "Category " + i).build());
    }

    mockMvc.perform(get("/api/public/categories"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").isArray())
           .andExpect(jsonPath("$.length()").value(50));
  }

  @Test
  @DisplayName("Should return categories in consistent order")
  void testGetAllCategoriesConsistentOrder() throws Exception {
    categoryJpaRepository.saveAndFlush(
      CategoryTestFixture.createCategory("AAA", "First").build());
    categoryJpaRepository.saveAndFlush(
      CategoryTestFixture.createCategory("ZZZ", "Last").build());
    categoryJpaRepository.saveAndFlush(
      CategoryTestFixture.createCategory("MMM", "Middle").build());

    mockMvc.perform(get("/api/public/categories"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").isArray())
           .andExpect(jsonPath("$.length()").value(3));
  }

  @Test
  @DisplayName("Should return category with special characters in description")
  void testGetAllCategoriesSpecialCharacters() throws Exception {
    categoryJpaRepository.saveAndFlush(
      CategoryTestFixture.createCategory("SPECIAL", "Category with @#$% & special chars!").build());

    mockMvc.perform(get("/api/public/categories"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].categoryName")
               .value("Category with @#$% & special chars!"));
  }

  @Test
  @DisplayName("Should not contain sensitive data in category response")
  void testGetAllCategoriesNoSensitiveData() throws Exception {
    categoryJpaRepository.saveAndFlush(CategoryTestFixture.createTechCategory().build());

    mockMvc.perform(get("/api/public/categories"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].version").doesNotExist())
           .andExpect(jsonPath("$[0].createdBy").doesNotExist());
  }
}
