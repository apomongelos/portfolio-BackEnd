/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.JobTitle;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface JobTitleService {

    public List<JobTitle> getJobTitles();

    public JobTitle getJobTitle(Long id);

    public JobTitle saveJobTitle(JobTitle jobTitle);

    public void deleteJobTitle(Long id);
}
