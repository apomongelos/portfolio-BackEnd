/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Project;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface ProjectService {

    public List<Project> getProjects();

    public Project getProject(Long id);

    public Project saveProject(Project Project);

    public void deleteProject(Long id);
}
