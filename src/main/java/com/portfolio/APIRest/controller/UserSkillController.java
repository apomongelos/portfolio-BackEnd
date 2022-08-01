/*
 * To change this license header, choose License Headers in UserSkill Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.UserSkillCreateDTO;
import com.portfolio.APIRest.dto.UserSkillDTO;
import com.portfolio.APIRest.dto.UserSkillUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.Skill;
import com.portfolio.APIRest.model.UserSkill;
import com.portfolio.APIRest.model.User;
import com.portfolio.APIRest.model.UserSkillId;
import com.portfolio.APIRest.service.SkillService;
import com.portfolio.APIRest.service.UserSkillService;
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
@RequestMapping(path = "api/users_skills")
public class UserSkillController {

    private final SkillService skillService;
    private final UserSkillService userSkillService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserSkillController(UserSkillService userSkillService, ModelMapper modelMapper, UserService userService, SkillService skillService) {
        this.userSkillService = userSkillService;
        this.modelMapper = modelMapper;
        this.skillService = skillService;
        this.userService = userService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<UserSkillDTO>> getUserSkills() {
        List<UserSkill> userSkills = userSkillService.getUserSkills();

        List<UserSkillDTO> userSkillsDTOs = userSkills
                .stream()
                .map(userSkill -> modelMapper.map(userSkill, UserSkillDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(userSkillsDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSkillDTO> getUserSkill(@PathVariable String id) {
        String[] parts = id.split("_");
        UserSkillId usId = new UserSkillId(Long.parseLong(parts[0]), Long.parseLong(parts[1]));

        UserSkill userSkill = userSkillService.getUserSkill(usId);
        if (userSkill == null) {
            throw new NotFoundException("not found user skill with id: " + id);
        }
        UserSkillDTO userSkillDTO = modelMapper.map(userSkill, UserSkillDTO.class);
        return ResponseEntity.ok().body(userSkillDTO);
    }

    @PostMapping
    public ResponseEntity<UserSkillDTO> addNewUserSkill(@Valid @RequestBody UserSkillCreateDTO userSkillDTO) {
        User usrExist = userService.getUser(userSkillDTO.getUserId());
        if (usrExist == null) {
            throw new NotFoundException("not found user with id: " + userSkillDTO.getUserId());
        }
        Skill skillExist = skillService.getSkill(userSkillDTO.getSkillId());
        if (skillExist == null) {
            throw new NotFoundException("not found skill with id: " + userSkillDTO.getSkillId());
        }

        UserSkill newUserSkill = new UserSkill();
        newUserSkill.setLevel(userSkillDTO.getLevel());

        newUserSkill.setUser(usrExist);
        newUserSkill.setSkill(skillExist);

        UserSkill newUserSkillCreated = userSkillService.saveUserSkill(newUserSkill);
        UserSkillDTO newUserSkillDTO = modelMapper.map(newUserSkillCreated, UserSkillDTO.class);
        return new ResponseEntity<>(newUserSkillDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSkillDTO> updateUserSkill(@PathVariable String id, @Valid @RequestBody UserSkillUpdateDTO userSkillDTO) {
        String[] parts = id.split("_");
        UserSkillId usId = new UserSkillId(Long.parseLong(parts[0]), Long.parseLong(parts[1]));

        UserSkill usrSkillToUpdate = userSkillService.getUserSkill(usId);

        if (usrSkillToUpdate == null) {
            throw new NotFoundException("not found user skill with id");
        }

        if (userSkillDTO.getUserId() != null) {
            User usrExist = userService.getUser(userSkillDTO.getUserId());
            if (usrExist == null) {
                throw new NotFoundException("not found user with id: " + userSkillDTO.getUserId());
            }
            usrSkillToUpdate.setUser(usrExist);
        }
        if (userSkillDTO.getSkillId() != null) {
            Skill skillExist = skillService.getSkill(userSkillDTO.getSkillId());
            if (skillExist == null) {
                throw new NotFoundException("not found skill with id: " + userSkillDTO.getSkillId());
            }
            usrSkillToUpdate.setSkill(skillExist);
        }

        if (userSkillDTO.getLevel() != null) {
            usrSkillToUpdate.setLevel(userSkillDTO.getLevel());
        }

//        UserSkillId usIdUpdate = new UserSkillId(userSkillDTO.getUserId(), userSkillDTO.getSkillId());
//        usrSkillToUpdate.setId(usIdUpdate);

        UserSkill userSkillUpdated = userSkillService.saveUserSkill(usrSkillToUpdate);
        UserSkillDTO newUserSkillDTO = modelMapper.map(userSkillUpdated, UserSkillDTO.class);
        return new ResponseEntity<>(newUserSkillDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserSkill(@PathVariable UserSkillId id) {
        try {
            userSkillService.deleteUserSkill(id);
        } catch (Exception e) {
            throw new NotFoundException("not found user skill with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully user skill with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
