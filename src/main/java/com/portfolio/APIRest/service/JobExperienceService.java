/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.JobExperience;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface JobExperienceService {

    public List<JobExperience> getJobExperiences();

    public JobExperience getJobExperience(Long id);

    public JobExperience saveJobExperience(JobExperience jobExperience);

    public void deleteJobExperience(Long id);
}
