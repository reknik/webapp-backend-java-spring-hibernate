package com.reknik.webAppDemoBackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.reknik.webAppDemoBackend.entity.dto.EmployeeDto;
import java.util.HashSet;
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
@Table(name = "companies")
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "companyID")
  private int id;

  @Column(name = "address")
  private String address;

  @Column(name = "phone")
  private String phone;

  @Column(name = "name")
  private String name;

  @JsonManagedReference
  @JsonBackReference
  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE})
  @JoinTable(name = "e_companies", joinColumns = @JoinColumn(name = "companyID"), inverseJoinColumns = @JoinColumn(name = "employeeID"))
  private Set<EmployeeDto> employees;

  public Company(int id, String address, String phone, String name) {
    this.id = id;
    this.address = address;
    this.phone = phone;
    this.name = name;
  }

  public Company() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<EmployeeDto> getEmployees() {
    return employees;
  }

  public void setEmployees(Set<EmployeeDto> employees) {
    this.employees = employees;
  }
}
