package com.reknik.hr.service.impl;

import com.reknik.hr.entity.Address;
import com.reknik.hr.entity.request.AddressAddRequest;
import com.reknik.hr.entity.Employee;
import com.reknik.hr.repository.AddressRepository;
import com.reknik.hr.repository.EmployeeRepository;
import com.reknik.hr.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<Address> getAddressById(long id) {
        return addressRepository.findById(id);
    }

    @Override
    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public void saveAddress(AddressAddRequest address) {
        Address addressEntity = Address.builder()
                .city(address.getCity())
                .addressDetails(getMaskedAddressDetails(address.getAddressDetails()))
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .build();

        Employee employee = employeeRepository.findById(address.getEmployeeId()).orElseThrow();
        addressEntity.setEmployee(employee);
        addressRepository.save(addressEntity);
    }

    private String getMaskedAddressDetails(String addressDetails) {
        return addressDetails.replaceAll(".{" + addressDetails.length() / 2 + "}$", "x");
    }

    @Override
    public void deleteAddressById(long id) {
        addressRepository.deleteById(id);
    }


}
