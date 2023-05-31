package com.reknik.hr.service.impl;

import com.reknik.hr.entity.Company;
import com.reknik.hr.entity.dto.CompanyDTO;
import com.reknik.hr.repository.CompanyRepository;
import com.reknik.hr.service.CompanyService;
import com.reknik.hr.util.AuthenticationHelperService;
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

    private final AuthenticationHelperService authenticationHelperService;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper, AuthenticationHelperService authenticationHelperService) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.authenticationHelperService = authenticationHelperService;
    }

    @Override
    public Optional<Company> getCompanyById(long id) {
        return companyRepository.findById(id);
    }

    @Override
    public List<CompanyDTO> getCompanies() {
        List<Long> companyIds = authenticationHelperService.getCurrentUserCompanies();
        return companyRepository.findAllById(companyIds)
                .stream()
                .map(company -> CompanyDTO.builder()
                        .id(company.getId())
                        .phone(company.getPhone())
                        .address(company.getAddress())
                        .name(company.getName())
                        .build())
                .toList();
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
