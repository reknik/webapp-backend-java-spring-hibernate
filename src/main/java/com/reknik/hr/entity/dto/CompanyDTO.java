package com.reknik.hr.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyDTO {

    private long id;

    private String address;

    private String phone;

    private String name;
}
