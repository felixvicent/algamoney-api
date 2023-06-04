package com.felps.algamoney.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(min = 3, max = 20)
  private String name;

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int hashCode(){
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object object){
    if(this == object){
      return true;
    }
    if(object == null) {
      return false;
    }
    if(getClass() != object.getClass()) {
      return false;
    }

    Category other = (Category) object;

    if(id == null) {
      if(other.id != null) {
        return false;
      }
    } else if(!id.equals(other.id)) {
      return false;
    }

    return true;
  }
}
