/*
 * To change this license header, choose License Headers in jobType Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.JobTypeCreateDTO;
import com.portfolio.APIRest.dto.JobTypeUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.JobType;
import com.portfolio.APIRest.service.JobTypeService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping(path = "api/job_types")
public class JobTypeController {

    private final JobTypeService jobTypeService;
    private final ModelMapper modelMapper;

    public JobTypeController(JobTypeService jobTypeService, ModelMapper modelMapper) {
        this.jobTypeService = jobTypeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<JobType>> getCountries() {
        List<JobType> countries = jobTypeService.getJobTypes();
        return ResponseEntity.ok().body(countries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobType> getJobType(@PathVariable Long id) {
        JobType jobType = jobTypeService.getJobType(id);
        if (jobType == null) {
            throw new NotFoundException("not found jobType with id: " + id);
        }

        return ResponseEntity.ok().body(jobType);
    }

    @PostMapping
    public ResponseEntity<JobType> addNewJobType(@Valid @RequestBody JobTypeCreateDTO jobTypeDTO) {
        JobType newJobType = modelMapper.map(jobTypeDTO, JobType.class);

        JobType newjobTypeCreated = jobTypeService.saveJobType(newJobType);
        return new ResponseEntity<>(newjobTypeCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobType> updateJobType(@PathVariable Long id, @Valid @RequestBody JobTypeUpdateDTO jobTypeDTO) {
        JobType jobTypeToUpdate = jobTypeService.getJobType(id);
        if (jobTypeToUpdate == null) {
            throw new NotFoundException("not found jobType with id");
        }

        if (jobTypeDTO.getName() != null) {
            jobTypeToUpdate.setName(jobTypeDTO.getName());
        }

        JobType jobTypeUpdated = jobTypeService.saveJobType(jobTypeToUpdate);
        return new ResponseEntity<>(jobTypeUpdated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJobType(@PathVariable Long id) {
        try {
            jobTypeService.deleteJobType(id);
        } catch (Exception e) {
            throw new NotFoundException("not found jobType with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully jobType with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
