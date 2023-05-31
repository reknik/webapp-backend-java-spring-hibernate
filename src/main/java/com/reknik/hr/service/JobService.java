package com.reknik.hr.service;

import com.reknik.hr.entity.Job;
import com.reknik.hr.entity.dto.JobDTO;
import com.reknik.hr.entity.request.JobAddRequest;

import java.util.List;
import java.util.Optional;

public interface JobService {

    Optional<Job> getJobById(long id);

    List<Job> getJobs();

    void saveJob(JobAddRequest job);

    void updateJob(JobDTO job);

    void deleteJobById(long id);
}
