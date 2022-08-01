/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.FacultyCreateDTO;
import com.portfolio.APIRest.dto.FacultyUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.Faculty;
import com.portfolio.APIRest.model.University;
import com.portfolio.APIRest.service.FacultyService;
import com.portfolio.APIRest.service.UniversityService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
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
@RequestMapping(path = "api/faculties")
public class FacultyController {

    private final FacultyService facultyService;
    private final UniversityService universityService;

    @Autowired
    public FacultyController(FacultyService facultyService, UniversityService universityService) {
        this.facultyService = facultyService;
        this.universityService = universityService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Faculty>> getFaculties() {
        List<Faculty> faculties = facultyService.getFaculties();
        return ResponseEntity.ok().body(faculties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.getFaculty(id);
        if (faculty == null) {
            throw new NotFoundException("not found faculty with id: " + id);
        }
//        ProjectDTO projectDTO = modelMapper.map(proje, ProjectDTO.class);
        return ResponseEntity.ok().body(faculty);
    }

    @PostMapping
    public ResponseEntity<Faculty> addNewFaculty(@Valid @RequestBody FacultyCreateDTO newFacultyDTO) {

        University universityExist = universityService.getUniversity(newFacultyDTO.getUniversityId());
        if (universityExist == null) {
            throw new NotFoundException("not found country with id: " + newFacultyDTO.getUniversityId());
        }

        Faculty newFaculty = new Faculty();
        newFaculty.setName(newFacultyDTO.getName());
        newFaculty.setUniversity(universityExist);
        if (newFacultyDTO.getUrl() != null) {
            newFaculty.setUrl(newFacultyDTO.getUrl());
        }
        if (newFacultyDTO.getImageUrl() != null) {
            newFaculty.setImageUrl(newFacultyDTO.getImageUrl());
        }

        Faculty newFacultyCreated = facultyService.saveFaculty(newFaculty);
//        FacultyDTO newFacultyDTO = modelMapper.map(newFacultyCreated, FacultyDTO.class);
        return new ResponseEntity<>(newFacultyCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @Valid @RequestBody FacultyUpdateDTO facUpdateDTO) {
        Faculty facToUpdate = facultyService.getFaculty(id);
        if (facToUpdate == null) {
            throw new NotFoundException("not found faculty with id");
        }

        if (facUpdateDTO.getUniversityId() != null) {
            University universityExist = universityService.getUniversity(facUpdateDTO.getUniversityId());
            if (universityExist == null) {
                throw new NotFoundException("not found university wiht id: " + facUpdateDTO.getUniversityId());
            }
            facToUpdate.setUniversity(universityExist);
        }
        if (facUpdateDTO.getName() != null) {
            facToUpdate.setName(facUpdateDTO.getName());
        }
        if (facUpdateDTO.getUrl() != null) {
            facToUpdate.setUrl(facUpdateDTO.getUrl());
        }
        if (facUpdateDTO.getImageUrl() != null) {
            facToUpdate.setImageUrl(facUpdateDTO.getImageUrl());
        }

        Faculty facUpdated = facultyService.saveFaculty(facToUpdate);
//        FacultyDTO newFacultyDTO = modelMapper.map(regUpdated, FacultyDTO.class);
        return new ResponseEntity<>(facUpdated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFaculty(@PathVariable Long id) {
        try {
            facultyService.deleteFaculty(id);
        } catch (Exception e) {
            throw new NotFoundException("not found faculty with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully faculty with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
