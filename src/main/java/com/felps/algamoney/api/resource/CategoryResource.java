package com.felps.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.felps.algamoney.api.model.Category;
import com.felps.algamoney.api.repository.CategoryRepository;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

  @Autowired
  private CategoryRepository categoryRepository;

  @GetMapping
  public List<Category> list() {
    return categoryRepository.findAll();
  }

  @PostMapping
  public ResponseEntity<Category> store(@RequestBody Category category, HttpServletResponse response) {
    Category newCategory = categoryRepository.save(category);
    
    URI uri = ServletUriComponentsBuilder
      .fromCurrentRequestUri()
      .path("/{id}")
      .buildAndExpand(newCategory.getId())
      .toUri();

    return ResponseEntity.created(uri).body(newCategory);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id){
    Optional<Category> category = categoryRepository.findById(id);

    return !category.isEmpty() ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
  }
}
