/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.ProjectCreateDTO;
import com.portfolio.APIRest.dto.ProjectDTO;
import com.portfolio.APIRest.dto.ProjectUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.Project;
import com.portfolio.APIRest.model.User;
import com.portfolio.APIRest.service.ProjectService;
import com.portfolio.APIRest.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping(path = "api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectController(ProjectService projectService, ModelMapper modelMapper, UserService userService) {
        this.projectService = projectService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ProjectDTO>> getProjects() {
        List<Project> projects = projectService.getProjects();

        List<ProjectDTO> projectsDTOs = projects
                .stream()
                .map(project -> modelMapper.map(project, ProjectDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(projectsDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Long id) {
        Project project = projectService.getProject(id);
        if (project == null) {
            throw new NotFoundException("not found project with id: " + id);
        }
        ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);
        return ResponseEntity.ok().body(projectDTO);
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> addNewProject(@Valid @RequestBody ProjectCreateDTO projectDTO) {
        User usrExist = userService.getUser(projectDTO.getUserId());
        if (usrExist == null) {
            throw new NotFoundException("not found user with id: " + projectDTO.getUserId());
        }

        Project newProject = new Project();
        newProject.setName(projectDTO.getName());
        newProject.setDescription(projectDTO.getDescription());

        newProject.setUser(usrExist);

        if (projectDTO.getUrl() != null) {
            newProject.setUrl(projectDTO.getUrl());
        }
        if (projectDTO.getGithubUrl() != null) {
            newProject.setGithubUrl(projectDTO.getGithubUrl());
        }

        Project newProjectCreated = projectService.saveProject(newProject);
        ProjectDTO newProjectDTO = modelMapper.map(newProjectCreated, ProjectDTO.class);
        return new ResponseEntity<>(newProjectDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectUpdateDTO projectDTO) {

        Project proToUpdate = projectService.getProject(id);

        if (proToUpdate == null) {
            throw new NotFoundException("not found project with id");
        }

        if (projectDTO.getUserId() != null) {
            User usrExist = userService.getUser(projectDTO.getUserId());
            if (usrExist == null) {
                throw new NotFoundException("not found user with id: " + projectDTO.getUserId());
            }
            proToUpdate.setUser(usrExist);
        }

        if (projectDTO.getName() != null) {
            proToUpdate.setName(projectDTO.getName());
        }
        if (projectDTO.getDescription() != null) {
            proToUpdate.setDescription(projectDTO.getDescription());
        }
        if (projectDTO.getUrl() != null) {
            proToUpdate.setUrl(projectDTO.getUrl());
        }
        if (projectDTO.getGithubUrl() != null) {
            proToUpdate.setGithubUrl(projectDTO.getGithubUrl());
        }

        Project projectUpdated = projectService.saveProject(proToUpdate);
        ProjectDTO newProjectDTO = modelMapper.map(projectUpdated, ProjectDTO.class);
        return new ResponseEntity<>(newProjectDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
        } catch (Exception e) {
            throw new NotFoundException("not found project with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully project with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
