package com.felps.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felps.algamoney.api.model.People;

public interface PeopleRepository extends JpaRepository<People, Long> {
  
}
