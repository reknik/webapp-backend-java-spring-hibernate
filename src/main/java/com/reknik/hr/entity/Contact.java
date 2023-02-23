package com.reknik.hr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
@Entity
@Table(name = "contact")
@Getter
@Setter
@DynamicUpdate
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "phone")
  private String phone;

  @Column(name = "email")
  private String email;
}
