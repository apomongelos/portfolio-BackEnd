/*
 * To change this license header, choose License Headers in university Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.UniversityCreateDTO;
import com.portfolio.APIRest.dto.UniversityUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.University;
import com.portfolio.APIRest.service.UniversityService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
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
@RequestMapping(path = "api/universities")
public class UniversityController {

    private final UniversityService universityService;
    private final ModelMapper modelMapper;

    public UniversityController(UniversityService universityService, ModelMapper modelMapper) {
        this.universityService = universityService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<University>> getUniversities() {
        List<University> universities = universityService.getUniversities();
        return ResponseEntity.ok().body(universities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<University> getUniversity(@PathVariable Long id) {
        University university = universityService.getUniversity(id);
        if (university == null) {
            throw new NotFoundException("not found university with id: " + id);
        }

        return ResponseEntity.ok().body(university);
    }

    @PostMapping
    public ResponseEntity<University> addNewUniversity(@Valid @RequestBody UniversityCreateDTO universityDTO) {
        University newUniversity = modelMapper.map(universityDTO, University.class);

        University newuniversityCreated = universityService.saveUniversity(newUniversity);
        return new ResponseEntity<>(newuniversityCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<University> updateUniversity(@PathVariable Long id, @Valid @RequestBody UniversityUpdateDTO universityDTO) {
        University uniToUpdate = universityService.getUniversity(id);
        if (uniToUpdate == null) {
            throw new NotFoundException("not found university with id");
        }

        if (universityDTO.getName() != null) {
            uniToUpdate.setName(universityDTO.getName());
        }
        if (universityDTO.getUrl() != null) {
            uniToUpdate.setUrl(universityDTO.getUrl());
        }
        if (universityDTO.getImageUrl() != null) {
            uniToUpdate.setImageUrl(universityDTO.getImageUrl());
        }

        University universityUpdated = universityService.saveUniversity(uniToUpdate);
        return new ResponseEntity<>(universityUpdated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUniversity(@PathVariable Long id) {
        try {
            universityService.deleteUniversity(id);
        } catch (Exception e) {
            throw new NotFoundException("not found university with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully university with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
