package com.reknik.webAppDemoBackend.dao;

import com.reknik.webAppDemoBackend.entity.Job;
import com.reknik.webAppDemoBackend.entity.dto.JobDto;
import java.util.List;
import java.util.Optional;

public interface JobDao {

  Optional<Job> getJobById(int id);

  List<Job> getJobs();

  void saveJob(Job job);

  void saveJob(JobDto job);

  void deleteJobById(int id);
}
