package com.reknik.hr.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter

public final class WebAppUserAuthority implements GrantedAuthority {

  @Column(name = "user_id")
  @Id
  private long userId;

  @Column(name = "role")
  @Id
  private String role;

  @Override
  public String getAuthority() {
    return this.role;
  }
}
