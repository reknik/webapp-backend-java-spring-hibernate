package com.reknik.webAppDemoBackend.service;

import com.reknik.webAppDemoBackend.dao.JobDao;
import com.reknik.webAppDemoBackend.entity.Job;
import com.reknik.webAppDemoBackend.entity.dto.JobDto;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class JobServiceImpl implements JobService {

  private final JobDao jobDao;

  @Autowired
  public JobServiceImpl(JobDao jobDao) {
    this.jobDao = jobDao;
  }

  @Override
  public Optional<Job> getJobById(int id) {
    return jobDao.getJobById(id);
  }

  @Override
  public List<Job> getJobs() {
    return jobDao.getJobs();
  }

  @Override
  public void saveJob(JobDto Job) {
    jobDao.saveJob(Job);
  }

  @Override
  public void updateJob(JobDto Job) {
    saveJob(Job);
  }

  @Override
  public void deleteJobById(int id) {
    jobDao.deleteJobById(id);
  }
}
