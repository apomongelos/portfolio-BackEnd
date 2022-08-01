/*
 * To change this license header, choose License Headers in skill Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.SkillCreateDTO;
import com.portfolio.APIRest.dto.SkillUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.Skill;
import com.portfolio.APIRest.service.SkillService;
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
@RequestMapping(path = "api/skills")
public class SkillController {

    private final SkillService skillService;
    private final ModelMapper modelMapper;

    public SkillController(SkillService skillService, ModelMapper modelMapper) {
        this.skillService = skillService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Skill>> getSkills() {
        List<Skill> skills = skillService.getSkills();
        return ResponseEntity.ok().body(skills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkill(@PathVariable Long id) {
        Skill skill = skillService.getSkill(id);
        if (skill == null) {
            throw new NotFoundException("not found skill with id: " + id);
        }

        return ResponseEntity.ok().body(skill);
    }

    @PostMapping
    public ResponseEntity<Skill> addNewSkill(@Valid @RequestBody SkillCreateDTO skillDTO) {
        Skill newSkill = modelMapper.map(skillDTO, Skill.class);

        Skill newskillCreated = skillService.saveSkill(newSkill);
        return new ResponseEntity<>(newskillCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @Valid @RequestBody SkillUpdateDTO skillDTO) {
        Skill skillToUpdate = skillService.getSkill(id);
        if (skillToUpdate == null) {
            throw new NotFoundException("not found skill with id");
        }

        if (skillDTO.getName() != null) {
            skillToUpdate.setName(skillDTO.getName());
        }

        Skill skillUpdated = skillService.saveSkill(skillToUpdate);
        return new ResponseEntity<>(skillUpdated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable Long id) {
        try {
            skillService.deleteSkill(id);
        } catch (Exception e) {
            throw new NotFoundException("not found skill with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully skill with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
