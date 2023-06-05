package com.felps.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felps.algamoney.api.model.Entry;

public interface EntryRepository extends JpaRepository<Entry, Long> {

}
