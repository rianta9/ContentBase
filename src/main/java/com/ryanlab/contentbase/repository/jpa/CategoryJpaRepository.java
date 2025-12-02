package com.ryanlab.contentbase.repository.jpa;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ryanlab.contentbase.core.repository.ICoreJpaRepository;
import com.ryanlab.contentbase.model.entity.Category;

@Repository
public interface CategoryJpaRepository extends ICoreJpaRepository<Category, String> {

  Optional<Category> findByCategoryCode(String categoryCode);

  Optional<Category> findByCategoryName(String categoryName);
}
