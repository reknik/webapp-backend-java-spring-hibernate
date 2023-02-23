package com.reknik.hr.service.impl;

import com.reknik.hr.entity.Company;
import com.reknik.hr.entity.dto.CompanyDTO;
import com.reknik.hr.repository.CompanyRepository;
import com.reknik.hr.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;

  private final ModelMapper modelMapper;

  @Autowired
  public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper) {
    this.companyRepository = companyRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public Optional<Company> getCompanyById(long id) {
    return companyRepository.findById(id);
  }

  @Override
  public List<Company> getCompanies() {
    return companyRepository.findAll();
  }

  @Override
  public void saveCompany(CompanyDTO company) {
    Company companyEntity = modelMapper.map(company, Company.class);
    companyRepository.save(companyEntity);
  }

  @Override
  public void updateCompany(CompanyDTO company) {
    saveCompany(company);
  }

  @Override
  public void deleteCompanyById(long id) {
    companyRepository.deleteById(id);
  }
}
