package com.reknik.hr.controller;

import com.reknik.hr.entity.dto.CompanyDTO;
import com.reknik.hr.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies(Principal principal) {
        return ResponseEntity.ok(companyService.getCompanies());
    }
}
