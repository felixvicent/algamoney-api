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
    People storedPeople = findPeopleById(id);

    BeanUtils.copyProperties(people, storedPeople, "id");

    peopleRepository.save(storedPeople);

    return storedPeople;
  }

  public void updateActive(Long id, Boolean active) {
    People storedPeople = findPeopleById(id);

    storedPeople.setActive(active);

    peopleRepository.save(storedPeople);
  }

  public People findPeopleById(Long id) {
    Optional<People> storedPeople = peopleRepository.findById(id);

    if (storedPeople.isEmpty()) {
      throw new EmptyResultDataAccessException(1);
    }
    return storedPeople.get();
  }

}
