package com.ryanlab.contentbase.controller.general.category;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryanlab.contentbase.model.dto.response.category.CategoryResponse;
import com.ryanlab.contentbase.service.general.category.GetCategoryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Public Category Controller Handles public read-only operations for categories
 */
@RestController
@RequestMapping("/api/public/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PublicCategoryController {

    GetCategoryService getCategoryService;

    /**
     * Get all categories GET /api/public/categories
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> response = getCategoryService.getAllCategories();
        return ResponseEntity.ok(response);
    }
}
