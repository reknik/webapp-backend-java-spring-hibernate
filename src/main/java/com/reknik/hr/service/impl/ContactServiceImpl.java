package com.reknik.hr.service.impl;

import com.reknik.hr.entity.Contact;
import com.reknik.hr.entity.Employee;
import com.reknik.hr.entity.dto.ContactDTO;
import com.reknik.hr.entity.request.ContactAddRequest;
import com.reknik.hr.repository.ContactRepository;
import com.reknik.hr.repository.EmployeeRepository;
import com.reknik.hr.service.ContactService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<Contact> getContactById(long id) {
        return contactRepository.findById(id);
    }

    @Override
    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    @Override
    public void saveContact(ContactAddRequest contact) {
        Contact contactEntity = contactRepository.save(Contact.builder()
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .build());
        Employee employee = employeeRepository.findById(contact.getEmployeeId()).orElseThrow();
        employee.getContacts().add(contactEntity);
        employeeRepository.save(employee);
    }

    @Override
    public void updateContact(ContactDTO contact) {
        Contact contactEntity = modelMapper.map(contact, Contact.class);
        contactRepository.save(contactEntity);
    }

    @Override
    public void deleteContactById(long id) {
        contactRepository.deleteById(id);
    }


}
