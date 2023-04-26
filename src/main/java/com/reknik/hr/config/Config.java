package com.reknik.hr.config;

import com.reknik.hr.entity.*;
import com.reknik.hr.entity.dto.EmployeeDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Slf4j
public class Config {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    TypeMap<Employee, EmployeeDTO> employeeMapper = modelMapper.createTypeMap(Employee.class, EmployeeDTO.class);
    employeeMapper.addMapping(employee -> employee.getAddresses().stream().map(Address::getId).toList(),
        EmployeeDTO::setAddresses);
    employeeMapper.addMapping(employee -> employee.getCompanies().stream().map(Company::getId).toList(),
        EmployeeDTO::setCompanies);
    employeeMapper.addMapping(employee -> employee.getContacts().stream().map(Contact::getId).toList(),
        EmployeeDTO::setContacts);
    employeeMapper.addMapping(employee -> employee.getJobs().stream().map(Job::getId).toList(), EmployeeDTO::setJobs);
    return modelMapper;
  }
}
