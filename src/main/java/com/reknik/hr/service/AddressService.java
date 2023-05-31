package com.reknik.hr.service;

import com.reknik.hr.entity.Address;
import com.reknik.hr.entity.request.AddressAddRequest;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    Optional<Address> getAddressById(long id);

    List<Address> getAddresses();

    void saveAddress(AddressAddRequest job);

    void deleteAddressById(long id);
}
