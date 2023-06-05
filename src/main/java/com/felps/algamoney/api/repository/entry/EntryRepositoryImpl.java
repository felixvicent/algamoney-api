package com.felps.algamoney.api.repository.entry;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.felps.algamoney.api.model.Entry;
import com.felps.algamoney.api.repository.filter.EntryFilter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EntryRepositoryImpl implements EntryRepositoryQuery {

  @PersistenceContext
  private EntityManager manager;

  @Override
  public Page<Entry> filter(EntryFilter entryFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Entry> criteria = builder.createQuery(Entry.class);

    Root<Entry> root = criteria.from(Entry.class);

    Predicate[] predicates = createRestrictions(entryFilter, builder, root);

    criteria.where(predicates);

    TypedQuery<Entry> query = manager.createQuery(criteria);

    addPaginationRestrictions(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, getTotal(entryFilter));
  }

  private Predicate[] createRestrictions(EntryFilter entryFilter, CriteriaBuilder builder, Root<Entry> root) {
    List<Predicate> predicates = new ArrayList<>();

    if (StringUtils.hasText(entryFilter.getDescription())) {
      predicates.add(builder.like(builder.lower(root.get("description")),
          "%" + entryFilter.getDescription().toLowerCase() + "%"));
    }

    if (entryFilter.getFromDueDate() != null) {
      predicates.add(builder.greaterThanOrEqualTo(root.get("dueDate"), entryFilter.getFromDueDate()));
    }

    if (entryFilter.getToDueDate() != null) {
      predicates.add(builder.lessThanOrEqualTo(root.get("dueDate"), entryFilter.getToDueDate()));
    }

    return predicates.toArray(new Predicate[predicates.size()]);
  }

  private void addPaginationRestrictions(TypedQuery<Entry> query, Pageable pageable) {
    int currentPage = pageable.getPageNumber();
    int pageSize = pageable.getPageSize();
    int firstRegister = currentPage * pageSize;

    query.setFirstResult(firstRegister);
    query.setMaxResults(pageSize);
  }

  private Long getTotal(EntryFilter entryFilter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<Entry> root = criteria.from(Entry.class);

    Predicate[] predicates = createRestrictions(entryFilter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));

    return manager.createQuery(criteria).getSingleResult();
  }
}
