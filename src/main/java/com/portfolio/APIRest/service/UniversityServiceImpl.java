/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.University;
import com.portfolio.APIRest.repository.UniversityRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class UniversityServiceImpl implements UniversityService {
    
    private final UniversityRepository universityRepository;
    
    @Autowired
    public UniversityServiceImpl(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }
    
    @Override
    public List<University> getUniversities() {
        return universityRepository.findAll();
    }
    
    @Override
    public University getUniversity(Long id) {
        return universityRepository.findById(id).orElse(null);
    }
    
    @Override
    public University saveUniversity(University university) {
        return universityRepository.save(university);
    }
    
    @Override
    public void deleteUniversity(Long id) {
        universityRepository.deleteById(id);
    }
    
}
