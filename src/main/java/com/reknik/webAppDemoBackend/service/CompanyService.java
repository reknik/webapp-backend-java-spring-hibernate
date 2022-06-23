package com.reknik.webAppDemoBackend.service;

import com.reknik.webAppDemoBackend.entity.Company;
import com.reknik.webAppDemoBackend.entity.dto.CompanyDto;
import java.util.List;
import java.util.Optional;

public interface CompanyService {

  Optional<Company> getCompanyById(int id);

  List<Company> getCompanies();

  void saveCompany(CompanyDto Company);

  void updateCompany(CompanyDto Company);

  void deleteCompanyById(int id);
}
