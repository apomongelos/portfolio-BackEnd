/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.JobExperience;
import com.portfolio.APIRest.repository.JobExperienceRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class JobExperienceServiceImpl implements JobExperienceService {
    
    private final JobExperienceRepository jobExperienceRepository;
    
    @Autowired
    public JobExperienceServiceImpl(JobExperienceRepository jobExperienceRepository) {
        this.jobExperienceRepository = jobExperienceRepository;
    }
    
    @Override
    public List<JobExperience> getJobExperiences() {
        return jobExperienceRepository.findAll();
    }
    
    @Override
    public JobExperience getJobExperience(Long id) {
        return jobExperienceRepository.findById(id).orElse(null);
    }
    
    @Override
    public JobExperience saveJobExperience(JobExperience jobExperience) {
        return jobExperienceRepository.save(jobExperience);
    }
    
    @Override
    public void deleteJobExperience(Long id) {
        jobExperienceRepository.deleteById(id);
    }
    
}
