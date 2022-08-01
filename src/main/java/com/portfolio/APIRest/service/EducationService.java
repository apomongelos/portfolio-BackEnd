/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Education;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface EducationService {

    public List<Education> getEducations();

    public Education getEducation(Long id);

    public Education saveEducation(Education education);    

    public void deleteEducation(Long id);
//    for all the methods in api rest, miss patch method
//    Boolean delete(Long id); /delete
}
