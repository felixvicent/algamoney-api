package com.felps.algamoney.api.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felps.algamoney.api.event.CreatedResourceEvent;
import com.felps.algamoney.api.model.Entry;
import com.felps.algamoney.api.repository.EntryRepository;
import com.felps.algamoney.api.repository.filter.EntryFilter;
import com.felps.algamoney.api.service.EntryService;
import com.felps.algamoney.api.service.exception.NonexistentOrInactivePeopleException;
import com.felps.algamoney.api.exceptionhandler.AlgamoneyExceptionHandler.Error;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/entries")
public class EntryResource {

  @Autowired
  private EntryRepository entryRepository;

  @Autowired
  private EntryService entryService;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public List<Entry> search(EntryFilter entryFilter) {
    return entryRepository.filter(entryFilter);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Entry> getById(@PathVariable Long id) {
    Optional<Entry> entry = entryRepository.findById(id);

    return !entry.isEmpty() ? ResponseEntity.ok(entry.get()) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Entry> store(@Valid @RequestBody Entry entry, HttpServletResponse response) {
    Entry newEntry = entryService.save(entry);

    publisher.publishEvent(new CreatedResourceEvent(this, response, newEntry.getId()));

    return ResponseEntity.status(HttpStatus.CREATED).body(newEntry);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    Optional<Entry> entry = entryRepository.findById(id);

    if (entry.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    entryRepository.delete(entry.get());

    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler({ NonexistentOrInactivePeopleException.class })
  public ResponseEntity<Object> handleNonexistentOrInactivePeopleException(NonexistentOrInactivePeopleException ex) {
    String userMessage = messageSource.getMessage("people.nonexistenteorinactive", null,
        LocaleContextHolder.getLocale());
    String devMessage = ex.toString();

    List<Error> errors = Arrays.asList(new Error(userMessage, devMessage));

    return ResponseEntity.badRequest().body(errors);
  }
}
