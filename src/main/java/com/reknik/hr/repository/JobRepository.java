package com.reknik.hr.repository;

import com.reknik.hr.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

  public Job findAllByTitleAndAndId(String title, long id);
}
