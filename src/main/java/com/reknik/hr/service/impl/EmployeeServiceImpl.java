package com.reknik.hr.service.impl;

import com.reknik.hr.entity.Company;
import com.reknik.hr.entity.Contact;
import com.reknik.hr.entity.Employee;
import com.reknik.hr.entity.Job;
import com.reknik.hr.entity.dto.AddressDTO;
import com.reknik.hr.entity.dto.ContactDTO;
import com.reknik.hr.entity.dto.EmployeeDTO;
import com.reknik.hr.entity.dto.JobDTO;
import com.reknik.hr.entity.request.AddressAddRequest;
import com.reknik.hr.entity.request.EmployeeAddRequest;
import com.reknik.hr.repository.CompanyRepository;
import com.reknik.hr.repository.ContactRepository;
import com.reknik.hr.repository.EmployeeRepository;
import com.reknik.hr.repository.JobRepository;
import com.reknik.hr.service.AddressService;
import com.reknik.hr.service.EmployeeService;
import com.reknik.hr.util.AuthenticationHelperService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final CompanyRepository companyRepository;

    private final AddressService addressService;

    private final JobRepository jobRepository;

    private final ContactRepository contactRepository;

    private final ModelMapper modelMapper;

    private final AuthenticationHelperService authenticationHelperService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeDao,
                               CompanyRepository companyRepository, AddressService addressService, JobRepository jobRepository, ContactRepository contactRepository, ModelMapper modelMapper,
                               AuthenticationHelperService authenticationHelperService) {
        this.employeeRepository = employeeDao;
        this.companyRepository = companyRepository;
        this.addressService = addressService;
        this.jobRepository = jobRepository;
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
        this.authenticationHelperService = authenticationHelperService;
    }

    @Override
    public EmployeeDTO getEmployeeById(long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isEmpty()) {
            throw new IllegalArgumentException("Couldn't find employee with id : " + id);
        }

        return EmployeeDTO.builder()
                .id(employeeOptional.get().getId())
                .firstName(employeeOptional.get().getFirstName())
                .companies(employeeOptional.get().getCompanies().stream().map(Company::getId).toList())
                .lastName(employeeOptional.get().getLastName())
                .drivingLicense(employeeOptional.get().isDrivingLicense())
                .build();
    }

    @Override
    @Transactional
    public List<EmployeeDTO> getEmployees() {

        List<Long> companies = authenticationHelperService.getCurrentUserCompanies();
        List<String> roles = authenticationHelperService.getCurrentUserRoles();
        if (!roles.contains("ADMIN")) {
            return employeeRepository.findAll().stream()
                    .filter(employee -> employee.getCompanies()
                            .stream()
                            .anyMatch(company -> companies.contains(company.getId())))
                    .map(employee -> EmployeeDTO.builder()
                            .id(employee.getId())
                            .firstName(employee.getFirstName())
                            .lastName(employee.getLastName())
                            .companies(employee.getCompanies().stream().map(Company::getId).toList())
                            .drivingLicense(employee.isDrivingLicense())
                            .build())
                    .toList();
        }
        return employeeRepository.findAll().stream().map(employee -> EmployeeDTO.builder()
                        .id(employee.getId())
                        .firstName(employee.getFirstName())
                        .companies(employee.getCompanies().stream().map(Company::getId).toList())
                        .lastName(employee.getLastName())
                        .drivingLicense(employee.isDrivingLicense())
                        .build())
                .toList();
    }

    @Override
    public void saveEmployee(EmployeeAddRequest employee) {
        Employee employeeEntity = Employee.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .drivingLicense(employee.isDrivingLicense())
                .build();
        if (employee.getCompanyId() != null) {
            employeeEntity.setCompanies(List.of(companyRepository.findById(employee.getCompanyId()).orElseThrow()));
        }

        if (employee.getContact() != null) {
            Contact contact = contactRepository.save(Contact.builder()
                    .email(employee.getContact().getEmail())
                    .phone(employee.getContact().getPhone())
                    .build());
            employeeEntity.setContacts(List.of(contact));
        }

        employeeEntity = employeeRepository.save(employeeEntity);

        if (employee.getAddress() != null) {
            addressService.saveAddress(AddressAddRequest.builder()
                    .employeeId(employeeEntity.getId())
                    .addressDetails(employee.getAddress().getAddressDetails())
                    .postalCode(employee.getAddress().getPostalCode())
                    .city(employee.getAddress().getCity())
                    .country(employee.getAddress().getCountry())
                    .build()
            );
        }

        if (employee.getJob() != null) {
            jobRepository.save(Job.builder()
                    .employees(Set.of(employeeEntity))
                    .title(employee.getJob().getTitle())
                    .build());
        }

    }

    @Override
    public void updateEmployee(EmployeeAddRequest employee) {
        employee.setAddress(null);
        employee.setContact(null);
        employee.setJob(null);
        saveEmployee(employee);
    }

    @Override
    public void deleteEmployeeById(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<JobDTO> getJobsByEmployeeId(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        return employee.getJobs().stream()
                .map(job -> new JobDTO(job.getId(), job.getTitle()))
                .toList();
    }

    @Override
    @Transactional
    public List<AddressDTO> getAddressesByEmployeeId(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        return employee.getAddresses().stream()
                .map(address -> AddressDTO.builder()
                        .id(address.getId())
                        .addressDetails(address.getAddressDetails())
                        .postalCode(address.getPostalCode())
                        .city(address.getCity())
                        .country(address.getCountry())
                        .build())
                .toList();
    }

    @Override
    public List<ContactDTO> getContactsByEmployeeId(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        return employee.getContacts().stream().map(contact -> ContactDTO.builder()
                .id(contact.getId())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build()).toList();
    }
}
