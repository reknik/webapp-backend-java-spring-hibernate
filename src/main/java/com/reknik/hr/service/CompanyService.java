package com.reknik.hr.service;

import com.reknik.hr.entity.Company;
import com.reknik.hr.entity.dto.CompanyDTO;
import java.util.List;
import java.util.Optional;

public interface CompanyService {

  Optional<Company> getCompanyById(long id);

  List<Company> getCompanies();

  void saveCompany(CompanyDTO company);

  void updateCompany(CompanyDTO company);

  void deleteCompanyById(long id);
}
