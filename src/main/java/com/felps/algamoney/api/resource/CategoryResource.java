package com.felps.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felps.algamoney.api.event.CreatedResourceEvent;
import com.felps.algamoney.api.model.Category;
import com.felps.algamoney.api.repository.CategoryRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public List<Category> list() {
    return categoryRepository.findAll();
  }

  @PostMapping
  public ResponseEntity<Category> store(@Valid @RequestBody Category category, HttpServletResponse response) {
    Category newCategory = categoryRepository.save(category);
    
    publisher.publishEvent(new CreatedResourceEvent(this, response, newCategory.getId()));

    return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id){
    Optional<Category> category = categoryRepository.findById(id);

    return !category.isEmpty() ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
  }
}
