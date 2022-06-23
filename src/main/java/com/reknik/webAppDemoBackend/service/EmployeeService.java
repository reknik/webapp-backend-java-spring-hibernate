package com.reknik.webAppDemoBackend.service;

import com.reknik.webAppDemoBackend.entity.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

  Optional<Employee> getEmployeeById(int id);

  List<Employee> getEmployees();

  void saveEmployee(Employee employee);

  void updateEmployee(Employee employee);

  void deleteEmployeeById(int id);
}
