/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.JobTitle;
import com.portfolio.APIRest.repository.JobTitleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class JobTitleServiceImpl implements JobTitleService {
    
    private final JobTitleRepository jobTitleRepository;
    
    @Autowired
    public JobTitleServiceImpl(JobTitleRepository jobTitleRepository) {
        this.jobTitleRepository = jobTitleRepository;
    }
    
    @Override
    public List<JobTitle> getJobTitles() {
        return jobTitleRepository.findAll();
    }
    
    @Override
    public JobTitle getJobTitle(Long id) {
        return jobTitleRepository.findById(id).orElse(null);
    }
    
    @Override
    public JobTitle saveJobTitle(JobTitle jobTitle) {
        return jobTitleRepository.save(jobTitle);
    }
    
    @Override
    public void deleteJobTitle(Long id) {
        jobTitleRepository.deleteById(id);
    }
    
}
