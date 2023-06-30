package com.reknik.hr.service;

import com.reknik.hr.entity.Contact;
import com.reknik.hr.entity.dto.ContactDTO;
import com.reknik.hr.entity.request.ContactAddRequest;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    Optional<Contact> getContactById(long id);

    List<Contact> getContacts();

    void saveContact(ContactAddRequest contact);

    void updateContact(ContactDTO contact);

    void deleteContactById(long id);
}
