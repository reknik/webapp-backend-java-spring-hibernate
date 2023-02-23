package com.reknik.hr.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@Entity
@Table(name = "job")
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate

public class Job {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "title")
  private String title;

  @JsonManagedReference
  @JsonBackReference
  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE})
  @JoinTable(name = "e_jobs", joinColumns = @JoinColumn(name = "job_id"),
      inverseJoinColumns = @JoinColumn(name = "employee_id"))
  private Set<Employee> employees;
}
