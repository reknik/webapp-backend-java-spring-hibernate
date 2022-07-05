package com.reknik.webAppDemoBackend.service;

import com.reknik.webAppDemoBackend.dao.CompanyDao;
import com.reknik.webAppDemoBackend.dao.EmployeeDao;
import com.reknik.webAppDemoBackend.dao.JobDao;
import com.reknik.webAppDemoBackend.entity.Employee;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeDao employeeDao;

  private final JobDao jobDao;

  private final CompanyDao companyDao;

  @Autowired
  public EmployeeServiceImpl(EmployeeDao employeeDao, JobDao jobDao, CompanyDao companyDao) {
    this.employeeDao = employeeDao;
    this.jobDao = jobDao;
    this.companyDao = companyDao;
  }

  @Override
  public Optional<Employee> getEmployeeById(int id) {
    return employeeDao.getEmployeeById(id);
  }

  @Override
  public List<Employee> getEmployees() {
    return employeeDao.getEmployees();
  }

  @Override
  public void saveEmployee(Employee employee) {
    if (employee.getJobs() != null) {
      employee.getJobs().forEach(jobDao::saveJob);
    }
    if (employee.getCompanies() != null) {
      employee.getCompanies().forEach(companyDao::saveCompany);
    }
    employeeDao.saveEmployee(employee);
  }

  @Override
  public void updateEmployee(Employee employee) {
    saveEmployee(employee);
  }

  @Override
  public void deleteEmployeeById(int id) {
    employeeDao.deleteEmployeeById(id);
  }
}
