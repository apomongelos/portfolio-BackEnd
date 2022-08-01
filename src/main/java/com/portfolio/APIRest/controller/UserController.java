/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.UserDTO;
import com.portfolio.APIRest.dto.UserUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.User;
import com.portfolio.APIRest.service.UserService;
import java.time.LocalDate;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {

        User user = userService.getUserByEmail(email);

        if (user == null) {
            throw new NotFoundException("not found user with first name and last name: " + email);
        }

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok().body(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateEstudio(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO usrUpdateDTO) {
        User usrExist = userService.getUser(usrUpdateDTO.getUserId());
        if (usrExist == null) {
            throw new NotFoundException("not found user with id: " + usrUpdateDTO.getUserId());
        }

        if (usrUpdateDTO.getFirstName() != null) {
            usrExist.setFirstName(usrUpdateDTO.getFirstName());
        }
        if (usrUpdateDTO.getLastName() != null) {
            usrExist.setLastName(usrUpdateDTO.getLastName());
        }
        if (usrUpdateDTO.getEmail() != null) {
            usrExist.setEmail(usrUpdateDTO.getEmail());
        }
        if (usrUpdateDTO.getAboutMe() != null) {
            usrExist.setAboutMe(usrUpdateDTO.getAboutMe());
        }
        if (usrUpdateDTO.getDateOfBirth() != null) {
            usrExist.setDateOfBirth(LocalDate.parse(usrUpdateDTO.getDateOfBirth()));
        }
        if (usrUpdateDTO.getUsername() != null) {
            usrExist.setUsername(usrUpdateDTO.getUsername());
        }

        User usrUpdated = userService.saveUser(usrExist);
        UserDTO newEducationDTO = modelMapper.map(usrUpdated, UserDTO.class);
        return new ResponseEntity<>(newEducationDTO, HttpStatus.CREATED);
    }
}
