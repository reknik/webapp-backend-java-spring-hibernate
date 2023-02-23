package com.reknik.hr.service;

import com.reknik.hr.entity.Employee;
import com.reknik.hr.entity.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

  EmployeeDTO getEmployeeById(long id);

  List<Employee> getEmployees();

  void saveEmployee(EmployeeDTO employee);

  void updateEmployee(EmployeeDTO employee);

  void deleteEmployeeById(long id);
}
