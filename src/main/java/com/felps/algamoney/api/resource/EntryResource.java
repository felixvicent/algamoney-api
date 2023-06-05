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
import com.felps.algamoney.api.model.Entry;
import com.felps.algamoney.api.repository.EntryRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/entries")
public class EntryResource {

  @Autowired
  private EntryRepository entryRepository;

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public List<Entry> list() {
    return entryRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Entry> getById(@PathVariable Long id) {
    Optional<Entry> entry = entryRepository.findById(id);

    return !entry.isEmpty() ? ResponseEntity.ok(entry.get()) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Entry> store(@Valid @RequestBody Entry entry, HttpServletResponse response) {
    Entry newEntry = entryRepository.save(entry);

    publisher.publishEvent(new CreatedResourceEvent(this, response, newEntry.getId()));

    return ResponseEntity.status(HttpStatus.CREATED).body(newEntry);
  }

}
