package com.reknik.hr.repository;

import com.reknik.hr.entity.WebAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<WebAppUser, Long> {

  public Optional<WebAppUser> findFirstByUsername(String username);
}
