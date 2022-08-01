/*
 * To change this license header, choose License Headers in JobExperience Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.JobExperienceCreateDTO;
import com.portfolio.APIRest.dto.JobExperienceDTO;
import com.portfolio.APIRest.dto.JobExperienceUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.City;
import com.portfolio.APIRest.model.Company;
import com.portfolio.APIRest.model.JobExperience;
import com.portfolio.APIRest.model.JobTitle;
import com.portfolio.APIRest.model.JobType;
import com.portfolio.APIRest.model.User;
import com.portfolio.APIRest.service.CityService;
import com.portfolio.APIRest.service.CompanyService;
import com.portfolio.APIRest.service.JobExperienceService;
import com.portfolio.APIRest.service.JobTitleService;
import com.portfolio.APIRest.service.JobTypeService;
import com.portfolio.APIRest.service.UserService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(path = "api/job_experiences")
public class JobExperienceController {

    private final CityService cityService;
    private final CompanyService companyService;
    private final JobExperienceService jobExperienceService;
    private final JobTitleService jobTitleService;
    private final JobTypeService jobTypeService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public JobExperienceController(CityService cityService, CompanyService companyService, JobExperienceService jobExperienceService, JobTitleService jobTitleService, JobTypeService jobTypeService, UserService userService, ModelMapper modelMapper) {
        this.cityService = cityService;
        this.companyService = companyService;
        this.jobExperienceService = jobExperienceService;
        this.jobTitleService = jobTitleService;
        this.jobTypeService = jobTypeService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<JobExperienceDTO>> getJobExperiences() {
        List<JobExperience> jobExperiences = jobExperienceService.getJobExperiences();

        List<JobExperienceDTO> jobExperiencesDTOs = jobExperiences
                .stream()
                .map(jobExperience -> modelMapper.map(jobExperience, JobExperienceDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(jobExperiencesDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobExperienceDTO> getJobExperience(@PathVariable Long id) {
        JobExperience jobExperience = jobExperienceService.getJobExperience(id);
        if (jobExperience == null) {
            throw new NotFoundException("not found job experience with id: " + id);
        }
        JobExperienceDTO jobExperienceDTO = modelMapper.map(jobExperience, JobExperienceDTO.class);
        return ResponseEntity.ok().body(jobExperienceDTO);
    }

    @PostMapping
    public ResponseEntity<JobExperienceDTO> addNewJobExperience(@Valid @RequestBody JobExperienceCreateDTO jobExperienceDTO) {
        User usrExist = userService.getUser(jobExperienceDTO.getUserId());
        if (usrExist == null) {
            throw new NotFoundException("not found user with id: " + jobExperienceDTO.getUserId());
        }
        City cityExist = cityService.getCity(jobExperienceDTO.getCityId());
        if (cityExist == null) {
            throw new NotFoundException("not found city with id: " + jobExperienceDTO.getCityId());
        }
        Company companyExist = companyService.getCompany(jobExperienceDTO.getCompanyId());
        if (companyExist == null) {
            throw new NotFoundException("not found company with id: " + jobExperienceDTO.getCompanyId());
        }
        JobType jobTypeExist = jobTypeService.getJobType(jobExperienceDTO.getJobTypeId());
        if (jobTypeExist == null) {
            throw new NotFoundException("not found job type with id: " + jobExperienceDTO.getJobTypeId());
        }
        JobTitle jobTitleExist = jobTitleService.getJobTitle(jobExperienceDTO.getJobTitleId());
        if (jobTitleExist == null) {
            throw new NotFoundException("not found job title with id: " + jobExperienceDTO.getJobTitleId());
        }

        JobExperience newJobExperience = new JobExperience();
        newJobExperience.setProfileDescription(jobExperienceDTO.getProfileDescription());
        newJobExperience.setStartDate(LocalDate.parse(jobExperienceDTO.getStartDate()));

        newJobExperience.setUser(usrExist);
        newJobExperience.setCity(cityExist);
        newJobExperience.setCompany(companyExist);
        newJobExperience.setJobType(jobTypeExist);
        newJobExperience.setJobTitle(jobTitleExist);

        if (jobExperienceDTO.getEndDate() != null) {
            newJobExperience.setEndDate(LocalDate.parse(jobExperienceDTO.getEndDate()));
        }
        if (jobExperienceDTO.getImageUrl() != null) {
            newJobExperience.setImageUrl(jobExperienceDTO.getImageUrl());
        }

        JobExperience newJobExperienceCreated = jobExperienceService.saveJobExperience(newJobExperience);
        JobExperienceDTO newJobExperienceDTO = modelMapper.map(newJobExperienceCreated, JobExperienceDTO.class);
        return new ResponseEntity<>(newJobExperienceDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobExperienceDTO> updateJobExperience(@PathVariable Long id, @Valid @RequestBody JobExperienceUpdateDTO jobExperienceDTO) {

        JobExperience jobExpToUpdate = jobExperienceService.getJobExperience(id);

        if (jobExpToUpdate == null) {
            throw new NotFoundException("not found job experience with id");
        }

        if (jobExperienceDTO.getUserId() != null) {
            User usrExist = userService.getUser(jobExperienceDTO.getUserId());
            if (usrExist == null) {
                throw new NotFoundException("not found user with id: " + jobExperienceDTO.getUserId());
            }
            jobExpToUpdate.setUser(usrExist);
        }
        if (jobExperienceDTO.getCityId() != null) {
            City cityExist = cityService.getCity(jobExperienceDTO.getCityId());
            if (cityExist == null) {
                throw new NotFoundException("not found city with id: " + jobExperienceDTO.getCityId());
            }
            jobExpToUpdate.setCity(cityExist);
        }
        if (jobExperienceDTO.getCompanyId() != null) {
            Company companyExist = companyService.getCompany(jobExperienceDTO.getCompanyId());
            if (companyExist == null) {
                throw new NotFoundException("not found company with id: " + jobExperienceDTO.getCompanyId());
            }
            jobExpToUpdate.setCompany(companyExist);
        }
        if (jobExperienceDTO.getJobTypeId() != null) {
            JobType jobTypeExist = jobTypeService.getJobType(jobExperienceDTO.getJobTypeId());
            if (jobTypeExist == null) {
                throw new NotFoundException("not found job type with id: " + jobExperienceDTO.getJobTypeId());
            }
            jobExpToUpdate.setJobType(jobTypeExist);
        }
        if (jobExperienceDTO.getJobTitleId() != null) {
            JobTitle jobTitleExist = jobTitleService.getJobTitle(jobExperienceDTO.getJobTitleId());
            if (jobTitleExist == null) {
                throw new NotFoundException("not found job title with id: " + jobExperienceDTO.getJobTitleId());
            }
            jobExpToUpdate.setJobTitle(jobTitleExist);
        }

        if (jobExperienceDTO.getStartDate() != null) {
            jobExpToUpdate.setStartDate(LocalDate.parse(jobExperienceDTO.getStartDate()));
        }
        if (jobExperienceDTO.getProfileDescription() != null) {
            jobExpToUpdate.setProfileDescription(jobExperienceDTO.getProfileDescription());
        }
        if (jobExperienceDTO.getEndDate() != null) {
            jobExpToUpdate.setEndDate(LocalDate.parse(jobExperienceDTO.getEndDate()));
        }
        if (jobExperienceDTO.getImageUrl() != null) {
            jobExpToUpdate.setImageUrl(jobExperienceDTO.getImageUrl());
        }

        JobExperience jobExperienceUpdated = jobExperienceService.saveJobExperience(jobExpToUpdate);
        JobExperienceDTO newJobExperienceDTO = modelMapper.map(jobExperienceUpdated, JobExperienceDTO.class);
        return new ResponseEntity<>(newJobExperienceDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJobExperience(@PathVariable Long id) {
        try {
            jobExperienceService.deleteJobExperience(id);
        } catch (Exception e) {
            throw new NotFoundException("not found job experience with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully job experience with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
