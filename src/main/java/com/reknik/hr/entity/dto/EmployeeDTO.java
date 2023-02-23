package com.reknik.hr.entity.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public final class EmployeeDTO {
  private long id;
  private String firstName;
  private String lastName;
  private boolean drivingLicense;
  private List<Long> addresses;
  private List<Long> contacts;
  private List<Long> companies;
  private List<Long> jobs;
  private Date birthDate;

}
