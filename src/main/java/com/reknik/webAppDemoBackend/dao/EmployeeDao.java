package com.reknik.webAppDemoBackend.dao;

import com.reknik.webAppDemoBackend.entity.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

  Optional<Employee> getEmployeeById(int id);

  List<Employee> getEmployees();

  void saveEmployee(Employee employee);

  void deleteEmployeeById(int id);
}
