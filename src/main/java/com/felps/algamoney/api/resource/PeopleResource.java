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

import com.felps.algamoney.api.model.People;
import com.felps.algamoney.api.repository.PeopleRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/peoples")
public class PeopleResource {
  @Autowired
  private PeopleRepository peopleRepository;

  @GetMapping
  public List<People> list() {
    return peopleRepository.findAll();
  }

  @PostMapping
  public ResponseEntity<People> store(@Valid @RequestBody People people, HttpServletResponse response){
    People newPeople = peopleRepository.save(people);

    URI uri = ServletUriComponentsBuilder
      .fromCurrentRequestUri()
      .path("/{id}")
      .buildAndExpand(newPeople.getId())
      .toUri();

    return ResponseEntity.created(uri).body(newPeople);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id){
    Optional<People> people = peopleRepository.findById(id);

    return !people.isEmpty() ? ResponseEntity.ok(people) : ResponseEntity.notFound().build();
  }
}
