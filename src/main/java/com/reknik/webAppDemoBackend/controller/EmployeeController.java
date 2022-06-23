package com.reknik.webAppDemoBackend.controller;

import com.reknik.webAppDemoBackend.entity.Employee;
import com.reknik.webAppDemoBackend.service.EmployeeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

  @Autowired
  EmployeeService employeeService;

  @GetMapping("/getById")
  public ResponseEntity<?> getById(@RequestParam(name = "id") int id) {
    Optional<Employee> employeeOptional;
    try {
      employeeOptional = employeeService.getEmployeeById(id);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return employeeOptional.map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
  public ResponseEntity<?> save(@RequestBody Employee employee) {
    try {
      employeeService.saveEmployee(employee);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/update")
  public ResponseEntity<?> update(@RequestBody Employee employee) {
    try {
      employeeService.updateEmployee(employee);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/deleteById")
  public ResponseEntity<?> deleteById(@RequestParam(name = "id") int id) {
    try {
      employeeService.deleteEmployeeById(id);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
