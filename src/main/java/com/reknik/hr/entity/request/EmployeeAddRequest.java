package com.reknik.hr.entity.request;

import com.reknik.hr.entity.dto.AddressDTO;
import com.reknik.hr.entity.dto.ContactDTO;
import com.reknik.hr.entity.dto.JobDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeAddRequest implements Serializable {

    private int id;

    private String firstName;

    private String lastName;

    private boolean drivingLicense;

    private AddressDTO address = new AddressDTO();

    private ContactDTO contact = new ContactDTO();

    private Long companyId;

    private JobDTO job = new JobDTO();

    private int birthYear;

}
