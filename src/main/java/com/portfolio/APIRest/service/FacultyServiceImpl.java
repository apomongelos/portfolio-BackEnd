/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Faculty;
import com.portfolio.APIRest.repository.FacultyRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class FacultyServiceImpl implements FacultyService {
    
    private final FacultyRepository facultyRepository;
    
    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }
    
    @Override
    public List<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }
    
    @Override
    public Faculty getFaculty(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }
    
    @Override
    public Faculty saveFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }
    
    @Override
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
    
}
