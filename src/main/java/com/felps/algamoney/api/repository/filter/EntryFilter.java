package com.felps.algamoney.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class EntryFilter {
  private String description;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fromDueDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate toDueDate;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getFromDueDate() {
    return fromDueDate;
  }

  public void setFromDueDate(LocalDate fromDueDate) {
    this.fromDueDate = fromDueDate;
  }

  public LocalDate getToDueDate() {
    return toDueDate;
  }

  public void setToDueDate(LocalDate toDueDate) {
    this.toDueDate = toDueDate;
  }
}
