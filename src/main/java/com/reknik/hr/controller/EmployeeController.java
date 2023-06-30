package com.reknik.hr.controller;

import com.reknik.hr.entity.dto.EmployeeDTO;
import com.reknik.hr.entity.request.EmployeeAddRequest;
import com.reknik.hr.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@PreAuthorize("principal.claims['roles'].contains('VIEWER')")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getById(@PathVariable(name = "employeeId") long id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @PostMapping("/save")
    @PreAuthorize("principal.claims['roles'].contains('EMPLOYEE')")
    public ResponseEntity<HttpStatus> save(@RequestBody EmployeeAddRequest employee) {
        employee.setId(0);
        employeeService.saveEmployee(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("principal.claims['roles'].contains('EMPLOYEE') ")
    public ResponseEntity<HttpStatus> update(@RequestBody EmployeeAddRequest employee) {
        employeeService.updateEmployee(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    @PreAuthorize("principal.claims['roles'].contains('ADMIN')")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable(name = "employeeId") long id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
