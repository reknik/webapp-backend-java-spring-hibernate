package com.reknik.webAppDemoBackend.controller;

import com.reknik.webAppDemoBackend.entity.Company;
import com.reknik.webAppDemoBackend.entity.dto.CompanyDto;
import com.reknik.webAppDemoBackend.service.CompanyService;
import java.util.List;
import java.util.Optional;
import net.gsdgroup.logging.Logger;
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
@RequestMapping("/api/company")
public class CompanyController {

  private final CompanyService companyService;

  @Autowired
  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  @GetMapping("/getById")
  public ResponseEntity<?> getById(@RequestParam(name = "id") int id) {
    Optional<Company> CompanyOptional;
    try {
      CompanyOptional = companyService.getCompanyById(id);
    } catch (Exception e) {
      Logger.warn("Couldn't fetch company for id " + id);
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return CompanyOptional.map(Company -> new ResponseEntity<>(Company, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping(path = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<?> getAll() {
    List<Company> companies;
    try {
      companies = companyService.getCompanies();
    } catch (Exception e) {
      Logger.warn("Couldn't fetch companies");
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(companies, HttpStatus.OK);
  }

  @PostMapping("/save")
  public ResponseEntity<?> save(@RequestBody CompanyDto company) {
    try {
      companyService.saveCompany(company);
    } catch (Exception e) {
      Logger.warn("Couldn't save company");
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/update")
  public ResponseEntity<?> update(@RequestBody CompanyDto company) {
    try {
      companyService.updateCompany(company);
    } catch (Exception e) {
      Logger.warn("Couldn't update company for id " + company.getId());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/deleteById")
  public ResponseEntity<?> deleteById(@RequestParam(name = "id") int id) {
    try {
      companyService.deleteCompanyById(id);
    } catch (Exception e) {
      Logger.warn("Couldn't delete company for id " + id);
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
