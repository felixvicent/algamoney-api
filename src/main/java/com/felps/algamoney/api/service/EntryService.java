package com.felps.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felps.algamoney.api.model.Entry;
import com.felps.algamoney.api.model.People;
import com.felps.algamoney.api.repository.EntryRepository;
import com.felps.algamoney.api.repository.PeopleRepository;
import com.felps.algamoney.api.service.exception.NonexistentOrInactivePeopleException;

@Service
public class EntryService {

  @Autowired
  private PeopleRepository peopleRepository;

  @Autowired
  private EntryRepository entryRepository;

  public Entry save(Entry entry) {
    Optional<People> people = peopleRepository.findById(entry.getPeople().getId());

    if (people.isEmpty() || people.get().isInactive()) {
      throw new NonexistentOrInactivePeopleException();
    }

    return entryRepository.save(entry);
  }
}
