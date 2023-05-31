package com.reknik.hr.controller;

import com.reknik.hr.entity.Address;
import com.reknik.hr.entity.request.AddressAddRequest;
import com.reknik.hr.entity.dto.AddressDTO;
import com.reknik.hr.service.AddressService;
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
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    private final EmployeeService employeeService;

    @Autowired
    public AddressController(AddressService addressService, EmployeeService employeeService) {
        this.addressService = addressService;
        this.employeeService = employeeService;
    }

    @GetMapping("/getById")
    public ResponseEntity<Address> getById(@RequestParam(name = "id") int id) {
        Optional<Address> addressOptional = addressService.getAddressById(id);
        return addressOptional.map(job -> new ResponseEntity<>(job, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Address>> getAll() {
        List<Address> addresses = null;
        try {
            addresses = addressService.getAddresses();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Serializable> save(@RequestBody AddressAddRequest address) {
        try {
            addressService.saveAddress(address);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getByEmployeeId/{employeeId}")
    public ResponseEntity<List<AddressDTO>> getByEmployeeId(@PathVariable("employeeId") long employeeId) {
        return new ResponseEntity<>(employeeService.getAddressesByEmployeeId(employeeId), HttpStatus.OK);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteById(@RequestParam(name = "id") int id) {
        try {
            addressService.deleteAddressById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
