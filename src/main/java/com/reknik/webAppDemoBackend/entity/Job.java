package com.reknik.webAppDemoBackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.reknik.webAppDemoBackend.entity.dto.EmployeeDto;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "jobs")
public class Job {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "jobID")
  private int id;

  @Column(name = "title")
  private String title;

  @JsonManagedReference
  @JsonBackReference
  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE})
  @JoinTable(name = "e_jobs", joinColumns = @JoinColumn(name = "jobID"), inverseJoinColumns = @JoinColumn(name = "employeeID"))
  private Set<EmployeeDto> employees;

  public Job(int id, String title) {
    this.id = id;
    this.title = title;
  }

  public Job() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Set<EmployeeDto> getEmployees() {
    return employees;
  }

  public void setEmployees(Set<EmployeeDto> employees) {
    this.employees = employees;
  }
}
