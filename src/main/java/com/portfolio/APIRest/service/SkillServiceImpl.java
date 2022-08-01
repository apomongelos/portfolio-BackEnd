/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Skill;
import com.portfolio.APIRest.repository.SkillRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class SkillServiceImpl implements SkillService {
    
    private final SkillRepository skillRepository;
    
    @Autowired
    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }
    
    @Override
    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }
    
    @Override
    public Skill getSkill(Long id) {
        return skillRepository.findById(id).orElse(null);
    }
    
    @Override
    public Skill saveSkill(Skill skill) {
        return skillRepository.save(skill);
    }
    
    @Override
    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }
    
}
