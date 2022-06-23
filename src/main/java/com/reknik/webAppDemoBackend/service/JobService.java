package com.reknik.webAppDemoBackend.service;

import com.reknik.webAppDemoBackend.entity.Job;
import com.reknik.webAppDemoBackend.entity.dto.JobDto;
import java.util.List;
import java.util.Optional;

public interface JobService {

  Optional<Job> getJobById(int id);

  List<Job> getJobs();

  void saveJob(JobDto job);

  void updateJob(JobDto job);

  void deleteJobById(int id);
}
