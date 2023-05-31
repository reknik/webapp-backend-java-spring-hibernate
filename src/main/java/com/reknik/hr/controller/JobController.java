package com.reknik.hr.controller;

import com.reknik.hr.entity.Job;
import com.reknik.hr.entity.dto.JobDTO;
import com.reknik.hr.entity.request.JobAddRequest;
import com.reknik.hr.service.EmployeeService;
import com.reknik.hr.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/job")
public class JobController {

    private final JobService jobService;

    private final EmployeeService employeeService;

    @Autowired
    public JobController(JobService jobService, EmployeeService employeeService) {
        this.jobService = jobService;
        this.employeeService = employeeService;
    }

    @GetMapping("/getById")
    public ResponseEntity<Job> getById(@RequestParam(name = "id") int id) {
        Optional<Job> jobOptional = jobService.getJobById(id);
        return jobOptional.map(job -> new ResponseEntity<>(job, HttpStatus.OK))
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
    public ResponseEntity<Serializable> save(@RequestBody JobAddRequest job) {
        try {
            jobService.saveJob(job);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody JobDTO job) {
        try {
            jobService.updateJob(job);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getByEmployeeId/{employeeId}")
    public ResponseEntity<List<JobDTO>> getByEmployeeId(@PathVariable("employeeId") long employeeId) {
        return new ResponseEntity<>(employeeService.getJobsByEmployeeId(employeeId), HttpStatus.OK);
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
