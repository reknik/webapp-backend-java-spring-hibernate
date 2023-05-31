package com.reknik.hr.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ContactDTO implements Serializable {

    private long id;

    private String phone;

    private String email;
}
