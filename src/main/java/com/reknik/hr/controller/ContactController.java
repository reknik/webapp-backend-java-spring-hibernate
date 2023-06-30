package com.reknik.hr.controller;

import com.reknik.hr.entity.Contact;
import com.reknik.hr.entity.dto.ContactDTO;
import com.reknik.hr.entity.request.ContactAddRequest;
import com.reknik.hr.service.ContactService;
import com.reknik.hr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService contactService;

    private final EmployeeService employeeService;

    @Autowired
    public ContactController(ContactService contactService, EmployeeService employeeService) {
        this.contactService = contactService;
        this.employeeService = employeeService;
    }

    @GetMapping("/getById")
    public ResponseEntity<Contact> getById(@RequestParam(name = "id") int id) {
        Optional<Contact> contactOptional = contactService.getContactById(id);
        return contactOptional.map(contact -> new ResponseEntity<>(contact, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Contact>> getAll() {
        List<Contact> contacts = null;
        try {
            contacts = contactService.getContacts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Serializable> save(@RequestBody ContactAddRequest contact) {
        try {
            contactService.saveContact(contact);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ContactDTO contact) {
        try {
            contactService.updateContact(contact);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getByEmployeeId/{employeeId}")
    public ResponseEntity<List<ContactDTO>> getByEmployeeId(@PathVariable("employeeId") long employeeId) {
        return new ResponseEntity<>(employeeService.getContactsByEmployeeId(employeeId), HttpStatus.OK);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteById(@RequestParam(name = "id") int id) {
        try {
            contactService.deleteContactById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
