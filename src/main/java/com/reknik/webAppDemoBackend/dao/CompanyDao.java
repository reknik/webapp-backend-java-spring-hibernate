package com.reknik.webAppDemoBackend.dao;

import com.reknik.webAppDemoBackend.entity.Company;
import com.reknik.webAppDemoBackend.entity.dto.CompanyDto;
import java.util.List;
import java.util.Optional;

public interface CompanyDao {

  Optional<Company> getCompanyById(int id);

  List<Company> getCompanies();

  void saveCompany(Company company);

  void saveCompany(CompanyDto company);

  void deleteCompanyById(int id);
}
