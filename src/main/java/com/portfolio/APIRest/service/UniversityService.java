/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.University;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface UniversityService {

    public List<University> getUniversities();

    public University getUniversity(Long id);

    public University saveUniversity(University university);

    public void deleteUniversity(Long id);
}
