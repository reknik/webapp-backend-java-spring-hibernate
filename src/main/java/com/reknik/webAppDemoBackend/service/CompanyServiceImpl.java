package com.reknik.webAppDemoBackend.service;

import com.reknik.webAppDemoBackend.dao.CompanyDao;
import com.reknik.webAppDemoBackend.entity.Company;
import com.reknik.webAppDemoBackend.entity.dto.CompanyDto;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

  private final CompanyDao companyDao;

  @Autowired
  public CompanyServiceImpl(CompanyDao companyDao) {
    this.companyDao = companyDao;
  }

  @Override
  public Optional<Company> getCompanyById(int id) {
    return companyDao.getCompanyById(id);
  }

  @Override
  public List<Company> getCompanies() {
    return companyDao.getCompanies();
  }

  @Override
  public void saveCompany(CompanyDto Company) {
    companyDao.saveCompany(Company);
  }

  @Override
  public void updateCompany(CompanyDto Company) {
    saveCompany(Company);
  }

  @Override
  public void deleteCompanyById(int id) {
    companyDao.deleteCompanyById(id);
  }
}
