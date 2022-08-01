/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Faculty;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface FacultyService {

    public List<Faculty> getFaculties();

    public Faculty getFaculty(Long id);

    public Faculty saveFaculty(Faculty faculty);    

    public void deleteFaculty(Long id);
}
