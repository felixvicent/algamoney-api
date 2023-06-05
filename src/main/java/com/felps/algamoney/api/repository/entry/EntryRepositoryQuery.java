package com.felps.algamoney.api.repository.entry;

import java.util.List;

import com.felps.algamoney.api.model.Entry;
import com.felps.algamoney.api.repository.filter.EntryFilter;

public interface EntryRepositoryQuery {
  public List<Entry> filter(EntryFilter entryFilter);
}
