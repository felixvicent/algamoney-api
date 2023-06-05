package com.felps.algamoney.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "entries")
public class Entry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;

  @Column(name = "due_date")
  private LocalDate dueDate;

  private LocalDate payday;

  private BigDecimal value;

  private String observation;

  @Enumerated(EnumType.STRING)
  private EntryType type;

  @ManyToOne
  @JoinColumn(name = "id_category")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "id_people")
  private People people;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public LocalDate getPayday() {
    return payday;
  }

  public void setPayday(LocalDate payday) {
    this.payday = payday;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public String getObservation() {
    return observation;
  }

  public void setObservation(String observation) {
    this.observation = observation;
  }

  public EntryType getType() {
    return type;
  }

  public void setType(EntryType type) {
    this.type = type;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public People getPeople() {
    return people;
  }

  public void setPeople(People people) {
    this.people = people;
  }
}
