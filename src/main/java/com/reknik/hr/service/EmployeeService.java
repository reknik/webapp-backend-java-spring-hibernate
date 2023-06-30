package com.reknik.hr.service;

import com.reknik.hr.entity.dto.AddressDTO;
import com.reknik.hr.entity.dto.ContactDTO;
import com.reknik.hr.entity.dto.EmployeeDTO;
import com.reknik.hr.entity.dto.JobDTO;
import com.reknik.hr.entity.request.EmployeeAddRequest;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO getEmployeeById(long id);

    List<EmployeeDTO> getEmployees();

    void saveEmployee(EmployeeAddRequest employee);

    void updateEmployee(EmployeeAddRequest employee);

    void deleteEmployeeById(long id);

    List<JobDTO> getJobsByEmployeeId(long employeeId);

    List<AddressDTO> getAddressesByEmployeeId(long employeeId);

    List<ContactDTO> getContactsByEmployeeId(long employeeId);
}
