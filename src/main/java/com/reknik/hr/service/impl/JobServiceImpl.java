package com.reknik.hr.service.impl;

import com.reknik.hr.entity.Job;
import com.reknik.hr.entity.dto.JobDTO;
import com.reknik.hr.repository.JobRepository;
import com.reknik.hr.service.JobService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JobServiceImpl implements JobService {

  private final JobRepository jobRepository;

  private final ModelMapper modelMapper;

  @Autowired
  public JobServiceImpl(JobRepository jobRepository, ModelMapper modelMapper) {
    this.jobRepository = jobRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public Optional<Job> getJobById(long id) {
    return jobRepository.findById(id);
  }

  @Override
  public List<Job> getJobs() {
    return jobRepository.findAll();
  }

  @Override
  public void saveJob(JobDTO job) {
    Job jobEntity = modelMapper.map(job, Job.class);
    jobRepository.save(jobEntity);
  }

  @Override
  public void updateJob(JobDTO job) {
    Job jobEntity = modelMapper.map(job, Job.class);
    jobRepository.save(jobEntity);
  }

  @Override
  public void deleteJobById(long id) {
    jobRepository.deleteById(id);
  }
}
