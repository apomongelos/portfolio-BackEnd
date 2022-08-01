/*
 * To change this license header, choose License Headers in jobTitle Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.JobTitleCreateDTO;
import com.portfolio.APIRest.dto.JobTitleUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.JobTitle;
import com.portfolio.APIRest.service.JobTitleService;
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
@RequestMapping(path = "api/job_titles")
public class JobTitleController {

    private final JobTitleService jobTitleService;
    private final ModelMapper modelMapper;

    public JobTitleController(JobTitleService jobTitleService, ModelMapper modelMapper) {
        this.jobTitleService = jobTitleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<JobTitle>> getCountries() {
        List<JobTitle> countries = jobTitleService.getJobTitles();
        return ResponseEntity.ok().body(countries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobTitle> getJobTitle(@PathVariable Long id) {
        JobTitle jobTitle = jobTitleService.getJobTitle(id);
        if (jobTitle == null) {
            throw new NotFoundException("not found jobTitle with id: " + id);
        }

        return ResponseEntity.ok().body(jobTitle);
    }

    @PostMapping
    public ResponseEntity<JobTitle> addNewJobTitle(@Valid @RequestBody JobTitleCreateDTO jobTitleDTO) {
        JobTitle newJobTitle = modelMapper.map(jobTitleDTO, JobTitle.class);

        JobTitle newjobTitleCreated = jobTitleService.saveJobTitle(newJobTitle);
        return new ResponseEntity<>(newjobTitleCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobTitle> updateJobTitle(@PathVariable Long id, @Valid @RequestBody JobTitleUpdateDTO jobTitleDTO) {
        JobTitle jobTitleToUpdate = jobTitleService.getJobTitle(id);
        if (jobTitleToUpdate == null) {
            throw new NotFoundException("not found jobTitle with id");
        }

        if (jobTitleDTO.getName() != null) {
            jobTitleToUpdate.setName(jobTitleDTO.getName());
        }

        JobTitle jobTitleUpdated = jobTitleService.saveJobTitle(jobTitleToUpdate);
        return new ResponseEntity<>(jobTitleUpdated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJobTitle(@PathVariable Long id) {
        try {
            jobTitleService.deleteJobTitle(id);
        } catch (Exception e) {
            throw new NotFoundException("not found jobTitle with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully jobTitle with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
