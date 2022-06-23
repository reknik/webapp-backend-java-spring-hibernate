package com.reknik.webAppDemoBackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.reknik.webAppDemoBackend.entity.dto.CompanyDto;
import com.reknik.webAppDemoBackend.entity.dto.JobDto;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "employeeID")
  private int id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "d_license")
  private boolean drivingLicense;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "employeeID")
  private List<Address> addresses;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "employeeID")
  private List<Contact> contacts;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "e_companies", joinColumns = @JoinColumn(name = "employeeID"), inverseJoinColumns = @JoinColumn(name = "companyID"))
  private Set<CompanyDto> companies;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "e_jobs", joinColumns = @JoinColumn(name = "employeeID"), inverseJoinColumns = @JoinColumn(name = "jobID"))
  private Set<JobDto> jobs;

  @Column(name = "year")
  private int birthYear;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public boolean isDrivingLicense() {
    return drivingLicense;
  }

  public void setDrivingLicense(boolean drivingLicense) {
    this.drivingLicense = drivingLicense;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }

  public Set<CompanyDto> getCompanies() {
    return companies;
  }

  public void setCompanies(Set<CompanyDto> companies) {
    this.companies = companies;
  }

  public Set<JobDto> getJobs() {
    return jobs;
  }

  public void setJobs(Set<JobDto> jobs) {
    this.jobs = jobs;
  }

  public int getBirthYear() {
    return birthYear;
  }

  public void setBirthYear(int birthYear) {
    this.birthYear = birthYear;
  }

  public int getAge() {
    return Year.now().getValue() - birthYear;
  }

}
