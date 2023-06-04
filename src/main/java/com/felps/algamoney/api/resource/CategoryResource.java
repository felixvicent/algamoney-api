package com.felps.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felps.algamoney.api.model.Category;
import com.felps.algamoney.api.repository.CategoryRepository;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

  @Autowired
  private CategoryRepository categoryRepository;

  @GetMapping
  public List<Category> list() {
    return categoryRepository.findAll();
  }
}
