package com.reknik.webAppDemoBackend.controller;

import com.reknik.webAppDemoBackend.entity.Job;
import com.reknik.webAppDemoBackend.entity.dto.JobDto;
import com.reknik.webAppDemoBackend.service.JobService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job")
public class JobController {

  private final JobService jobService;

  @Autowired
  public JobController(JobService jobService) {
    this.jobService = jobService;
  }

  @GetMapping("/getById")
  public ResponseEntity<?> getById(@RequestParam(name = "id") int id) {
    Optional<Job> JobOptional;
    try {
      JobOptional = jobService.getJobById(id);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return JobOptional.map(Job -> new ResponseEntity<>(Job, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping(path = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<List<Job>> getAll() {
    List<Job> jobs = null;
    try {
      jobs = jobService.getJobs();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>(jobs, HttpStatus.OK);
  }

  @PostMapping("/save")
  public ResponseEntity<?> save(@RequestBody JobDto job) {
    try {
      jobService.saveJob(job);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/update")
  public ResponseEntity<?> update(@RequestBody JobDto job) {
    try {
      jobService.updateJob(job);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/deleteById")
  public ResponseEntity<?> deleteById(@RequestParam(name = "id") int id) {
    try {
      jobService.deleteJobById(id);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
