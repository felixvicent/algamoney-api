package com.felps.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felps.algamoney.api.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  
}
