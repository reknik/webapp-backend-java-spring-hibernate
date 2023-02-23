package com.reknik.hr.service.impl;

import com.reknik.hr.entity.Company;
import com.reknik.hr.entity.Employee;
import com.reknik.hr.entity.Job;
import com.reknik.hr.entity.dto.EmployeeDTO;
import com.reknik.hr.repository.CompanyRepository;
import com.reknik.hr.repository.EmployeeRepository;
import com.reknik.hr.repository.JobRepository;
import com.reknik.hr.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;

  private final JobRepository jobRepository;

  private final CompanyRepository companyRepository;

  private final ModelMapper modelMapper;

  @Autowired
  public EmployeeServiceImpl(EmployeeRepository employeeDao, JobRepository jobDao,
                             CompanyRepository companyRepository, ModelMapper modelMapper) {
    this.employeeRepository = employeeDao;
    this.jobRepository = jobDao;
    this.companyRepository = companyRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public EmployeeDTO getEmployeeById(long id) {
    Optional<Employee> employeeOptional = employeeRepository.findById(id);
    if (employeeOptional.isEmpty()) {
      throw new IllegalArgumentException("Couldn't find employee with id : " + id);
    }

    return modelMapper.map(employeeOptional.get(), EmployeeDTO.class);
  }

  @Override
  public List<Employee> getEmployees() {
    return employeeRepository.findAll();
  }

  @Override
  public void saveEmployee(EmployeeDTO employee) {
    Employee employeeEntity = modelMapper.map(employee, Employee.class);
    List<Job> jobs = jobRepository.findAllById(employee.getJobs());
    employeeEntity.setJobs(jobs);
    List<Company> companies = companyRepository.findAllById(employee.getCompanies());
    employeeEntity.setCompanies(companies);
    employeeRepository.save(employeeEntity);
  }

  @Override
  public void updateEmployee(EmployeeDTO employee) {
    saveEmployee(employee);
  }

  @Override
  public void deleteEmployeeById(long id) {
    employeeRepository.deleteById(id);
  }
}
