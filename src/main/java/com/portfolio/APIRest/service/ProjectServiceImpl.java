/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Project;
import com.portfolio.APIRest.repository.ProjectRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    
    private final ProjectRepository projectRepository;
    
    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }
    
    @Override
    public Project getProject(Long id) {
        return projectRepository.findById(id).orElse(null);
    }
    
    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }
    
    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
    
}
