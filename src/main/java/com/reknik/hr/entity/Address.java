package com.reknik.hr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "address")
@Getter
@Setter
@DynamicUpdate
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "city")
  private String city;

  @Column(name = "address_details")
  private String addressDetails;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "country")
  private String country;
}
