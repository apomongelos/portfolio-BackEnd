/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.JobType;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface JobTypeService {

    public List<JobType> getJobTypes();

    public JobType getJobType(Long id);

    public JobType saveJobType(JobType jobType);

    public void deleteJobType(Long id);
}
