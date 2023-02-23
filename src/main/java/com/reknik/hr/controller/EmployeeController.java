package com.reknik.hr.controller;

import com.reknik.hr.entity.Employee;
import com.reknik.hr.entity.dto.EmployeeDTO;
import com.reknik.hr.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/getById")
  public ResponseEntity<EmployeeDTO> getById(@RequestParam(name = "id") long id) {
    return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
  }

  @GetMapping(path = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
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
  public ResponseEntity<HttpStatus> save(@RequestBody EmployeeDTO employee) {
    employeeService.saveEmployee(employee);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/update")
  public ResponseEntity<HttpStatus> update(@RequestBody EmployeeDTO employee) {
    employeeService.updateEmployee(employee);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/deleteById")
  public ResponseEntity<HttpStatus> deleteById(@RequestParam(name = "id") int id) {
    employeeService.deleteEmployeeById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
