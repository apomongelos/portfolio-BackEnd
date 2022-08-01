/*
 * To change this license header, choose License Headers in UserJobTitle Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.UserJobTitleCreateDTO;
import com.portfolio.APIRest.dto.UserJobTitleDTO;
import com.portfolio.APIRest.dto.UserJobTitleUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.JobTitle;
import com.portfolio.APIRest.model.UserJobTitle;
import com.portfolio.APIRest.model.User;
import com.portfolio.APIRest.model.UserJobTitleId;
import com.portfolio.APIRest.service.JobTitleService;
import com.portfolio.APIRest.service.UserJobTitleService;
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
@RequestMapping(path = "api/users_job_titles")
public class UserJobTitleController {

    private final JobTitleService jobTitleService;
    private final UserJobTitleService userJobTitleService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserJobTitleController(UserJobTitleService userJobTitleService, ModelMapper modelMapper, UserService userService, JobTitleService jobTitleService) {
        this.jobTitleService = jobTitleService;
        this.userJobTitleService = userJobTitleService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<UserJobTitleDTO>> getUserJobTitles() {
        List<UserJobTitle> userJobTitles = userJobTitleService.getUserJobTitles();

        List<UserJobTitleDTO> userJobTitlesDTOs = userJobTitles
                .stream()
                .map(userJobTitle -> modelMapper.map(userJobTitle, UserJobTitleDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(userJobTitlesDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserJobTitleDTO> getUserJobTitle(@PathVariable UserJobTitleId id) {
        UserJobTitle userJobTitle = userJobTitleService.getUserJobTitle(id);
        if (userJobTitle == null) {
            throw new NotFoundException("not found user job title with id: " + id);
        }
        UserJobTitleDTO userJobTitleDTO = modelMapper.map(userJobTitle, UserJobTitleDTO.class);
        return ResponseEntity.ok().body(userJobTitleDTO);
    }

    @PostMapping
    public ResponseEntity<UserJobTitleDTO> addNewUserJobTitle(@Valid @RequestBody UserJobTitleCreateDTO userJobTitleDTO) {
        User usrExist = userService.getUser(userJobTitleDTO.getUserId());
        if (usrExist == null) {
            throw new NotFoundException("not found user with id: " + userJobTitleDTO.getUserId());
        }

        JobTitle jobTitleExist = jobTitleService.getJobTitle(userJobTitleDTO.getJobTitleId());
        if (jobTitleExist == null) {
            throw new NotFoundException("not found job title with id: " + userJobTitleDTO.getJobTitleId());
        }

        UserJobTitle newUserJobTitle = new UserJobTitle();

        newUserJobTitle.setUser(usrExist);
        newUserJobTitle.setJobTitle(jobTitleExist);

        UserJobTitle newUserJobTitleCreated = userJobTitleService.saveUserJobTitle(newUserJobTitle);
        UserJobTitleDTO newUserJobTitleDTO = modelMapper.map(newUserJobTitleCreated, UserJobTitleDTO.class);
        return new ResponseEntity<>(newUserJobTitleDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserJobTitleDTO> updateUserJobTitle(@PathVariable UserJobTitleId id, @Valid @RequestBody UserJobTitleUpdateDTO userJobTitleDTO) {

        UserJobTitle usrJobTitleToUpdate = userJobTitleService.getUserJobTitle(id);

        if (usrJobTitleToUpdate == null) {
            throw new NotFoundException("not found user job title with id");
        }

        if (userJobTitleDTO.getUserId() != null) {
            User usrExist = userService.getUser(userJobTitleDTO.getUserId());
            if (usrExist == null) {
                throw new NotFoundException("not found user with id: " + userJobTitleDTO.getUserId());
            }
            usrJobTitleToUpdate.setUser(usrExist);
        }
        if (userJobTitleDTO.getJobTitleId() != null) {
            JobTitle jobTitleExist = jobTitleService.getJobTitle(userJobTitleDTO.getJobTitleId());
            if (jobTitleExist == null) {
                throw new NotFoundException("not found job title with id: " + userJobTitleDTO.getJobTitleId());
            }
            usrJobTitleToUpdate.setJobTitle(jobTitleExist);
        }

        UserJobTitle userJobTitleUpdated = userJobTitleService.saveUserJobTitle(usrJobTitleToUpdate);
        UserJobTitleDTO newUserJobTitleDTO = modelMapper.map(userJobTitleUpdated, UserJobTitleDTO.class);
        return new ResponseEntity<>(newUserJobTitleDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserJobTitle(@PathVariable UserJobTitleId id) {
        try {
            userJobTitleService.deleteUserJobTitle(id);
        } catch (Exception e) {
            throw new NotFoundException("not found user job title with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully user job title with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
