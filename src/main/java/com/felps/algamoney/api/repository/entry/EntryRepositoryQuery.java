package com.felps.algamoney.api.repository.entry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.felps.algamoney.api.model.Entry;
import com.felps.algamoney.api.repository.filter.EntryFilter;

public interface EntryRepositoryQuery {
  public Page<Entry> filter(EntryFilter entryFilter, Pageable pageable);
}
