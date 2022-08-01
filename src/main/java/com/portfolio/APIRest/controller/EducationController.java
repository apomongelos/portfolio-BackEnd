/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.EducationCreateDTO;
import com.portfolio.APIRest.dto.EducationDTO;
import com.portfolio.APIRest.dto.EducationUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.Education;
import com.portfolio.APIRest.model.Faculty;
import com.portfolio.APIRest.model.User;
import com.portfolio.APIRest.service.EducationService;
import com.portfolio.APIRest.service.FacultyService;
import com.portfolio.APIRest.service.UserService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(path = "api/educations")
@Slf4j
public class EducationController {

    private final EducationService educationService;
    private final FacultyService facultyService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public EducationController(EducationService educationService, ModelMapper modelMapper, UserService userService, FacultyService facultyService) {
        this.educationService = educationService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.facultyService = facultyService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<EducationDTO>> getEstudios() {
        List<Education> educations = educationService.getEducations();

        List<EducationDTO> educationsDTOs = educations
                .stream()
                .map(education -> modelMapper.map(education, EducationDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(educationsDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationDTO> getEstudio(@PathVariable Long id) {
        Education edu = educationService.getEducation(id);
        if (edu == null) {
            throw new NotFoundException("not found education with id: " + id);
        }

        EducationDTO educationDTO = modelMapper.map(edu, EducationDTO.class);
        return ResponseEntity.ok().body(educationDTO);
    }

    @PostMapping
    public ResponseEntity<EducationDTO> addNewEstudio(@Valid @RequestBody EducationCreateDTO nuevoEstudio) {

        Faculty facExist = facultyService.getFaculty(nuevoEstudio.getFacultyId());
        if (facExist == null) {
            throw new NotFoundException("not found faculty with id: " + nuevoEstudio.getFacultyId());
        }
        User usrExist = userService.getUser(nuevoEstudio.getUserId());
        if (usrExist == null) {
            throw new NotFoundException("not found user with id: " + nuevoEstudio.getUserId());
        }

        Education newEducation = new Education();
        newEducation.setDegreeName(nuevoEstudio.getDegreeName());
        newEducation.setStartingDate(LocalDate.parse(nuevoEstudio.getStartingDate()));

        if (nuevoEstudio.getCompletitionDate() != null) {
            newEducation.setCompletitionDate(LocalDate.parse(nuevoEstudio.getCompletitionDate()));
        }
        newEducation.setUser(usrExist);
        newEducation.setFaculty(facExist);

        Education newEducationCreated = educationService.saveEducation(newEducation);
        EducationDTO newEducationDTO = modelMapper.map(newEducationCreated, EducationDTO.class);
        return new ResponseEntity<>(newEducationDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EducationDTO> updateEstudio(@PathVariable Long id, @Valid @RequestBody EducationUpdateDTO eduUpdateDTO) {      
        Education eduToUpdate = educationService.getEducation(id);
        if (eduToUpdate == null) {
            throw new NotFoundException("not found education with id");
        }

//        here we can check is the data can change, double condition
        if (eduUpdateDTO.getUserId() != null) {
            User usrExist = userService.getUser(eduUpdateDTO.getUserId());
            if (usrExist == null) {
                throw new NotFoundException("not found user with id: " + eduUpdateDTO.getUserId());
            }
            eduToUpdate.setUser(usrExist);
        }
        if (eduUpdateDTO.getFacultyId() != null) {
            Faculty facExist = facultyService.getFaculty(eduUpdateDTO.getFacultyId());
            if (facExist == null) {
                throw new NotFoundException("not found faculty wiht id: " + eduUpdateDTO.getFacultyId());
            }
            eduToUpdate.setFaculty(facExist);
        }
        if (eduUpdateDTO.getDegreeName() != null) {
            eduToUpdate.setDegreeName(eduUpdateDTO.getDegreeName());
        }
        if (eduUpdateDTO.getStartingDate() != null) {
            eduToUpdate.setStartingDate(LocalDate.parse(eduUpdateDTO.getStartingDate()));
        }
        if (eduUpdateDTO.getCompletitionDate() != null) {
            eduToUpdate.setCompletitionDate(LocalDate.parse(eduUpdateDTO.getCompletitionDate()));
        }

        Education eduUpdated = educationService.saveEducation(eduToUpdate);
        EducationDTO newEducationDTO = modelMapper.map(eduUpdated, EducationDTO.class);
        return new ResponseEntity<>(newEducationDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEstudio(@PathVariable Long id) {
        try {
            educationService.deleteEducation(id);
        } catch (Exception e) {
            throw new NotFoundException("not found education with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully education with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
