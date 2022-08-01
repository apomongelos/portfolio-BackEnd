/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.JobType;
import com.portfolio.APIRest.repository.JobTypeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class JobTypeServiceImpl implements JobTypeService {

    private final JobTypeRepository jobTypeRepository;

    @Autowired
    public JobTypeServiceImpl(JobTypeRepository jobTypeRepository) {
        this.jobTypeRepository = jobTypeRepository;
    }

    @Override
    public List<JobType> getJobTypes() {
        return jobTypeRepository.findAll();
    }

    @Override
    public JobType getJobType(Long id) {
        return jobTypeRepository.findById(id).orElse(null);
    }

    @Override
    public JobType saveJobType(JobType jobType) {
        return jobTypeRepository.save(jobType);
    }

    @Override
    public void deleteJobType(Long id) {
        jobTypeRepository.deleteById(id);
    }

}
