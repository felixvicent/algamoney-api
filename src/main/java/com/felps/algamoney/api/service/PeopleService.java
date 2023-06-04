package com.felps.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.felps.algamoney.api.model.People;
import com.felps.algamoney.api.repository.PeopleRepository;

@Service
public class PeopleService {
  @Autowired
  private PeopleRepository peopleRepository;

  public People update(Long id, People people) {
    Optional<People> storedPeople = peopleRepository.findById(id);

    if (storedPeople.isEmpty()) {
      throw new EmptyResultDataAccessException(1);
    }

    BeanUtils.copyProperties(people, storedPeople.get(), "id");

    peopleRepository.save(storedPeople.get());

    return storedPeople.get();
  }
}
