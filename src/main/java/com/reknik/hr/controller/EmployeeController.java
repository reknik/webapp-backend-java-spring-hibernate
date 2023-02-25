package com.reknik.hr.controller;

import com.reknik.hr.entity.Employee;
import com.reknik.hr.entity.dto.EmployeeDTO;
import com.reknik.hr.security.WebAppUserRoles;
import com.reknik.hr.service.EmployeeService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/{employeeId}")
  @RolesAllowed({"ADMIN","USER","EMPLOYEE"})
  public ResponseEntity<EmployeeDTO> getById(@PathVariable(name = "employeeId") long id) {
    return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
  }

  @GetMapping(path = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
  @RolesAllowed({"ADMIN","USER","EMPLOYEE"})
  public ResponseEntity<List<Employee>> getAll() {
    List<Employee> employees = null;
    try {
      employees = employeeService.getEmployees();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>(employees, HttpStatus.OK);
  }

  @PostMapping("/save")
  @RolesAllowed({"ADMIN","EMPLOYEE"})
  public ResponseEntity<HttpStatus> save(@RequestBody EmployeeDTO employee) {
    employeeService.saveEmployee(employee);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/update")
  @RolesAllowed({"ADMIN","EMPLOYEE"})
  public ResponseEntity<HttpStatus> update(@RequestBody EmployeeDTO employee) {
    employeeService.updateEmployee(employee);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{employeeId}")
  @RolesAllowed({"ADMIN","EMPLOYEE"})
  public ResponseEntity<HttpStatus> deleteById(@PathVariable(name = "employeeId") long id) {
    employeeService.deleteEmployeeById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
