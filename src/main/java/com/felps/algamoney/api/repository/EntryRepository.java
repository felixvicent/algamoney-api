package com.felps.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felps.algamoney.api.model.Entry;
import com.felps.algamoney.api.repository.entry.EntryRepositoryQuery;

public interface EntryRepository extends JpaRepository<Entry, Long>, EntryRepositoryQuery {

}
