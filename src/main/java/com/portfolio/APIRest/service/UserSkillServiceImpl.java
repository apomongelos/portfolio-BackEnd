/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.UserSkill;
import com.portfolio.APIRest.model.UserSkillId;
import com.portfolio.APIRest.repository.UserSkillRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class UserSkillServiceImpl implements UserSkillService {

    private final UserSkillRepository userSkillRepository;

    @Autowired
    public UserSkillServiceImpl(UserSkillRepository userSkillRepository) {
        this.userSkillRepository = userSkillRepository;
    }

    @Override
    public List<UserSkill> getUserSkills() {
        return userSkillRepository.findAll();
    }

    @Override
    public UserSkill getUserSkill(UserSkillId userSkillId) {
        return userSkillRepository.findById(userSkillId).orElse(null);
    }

    @Override
    public UserSkill saveUserSkill(UserSkill userSkill) {
        return userSkillRepository.save(userSkill);
    }

    @Override
    public void deleteUserSkill(UserSkillId userSkillId) {
        userSkillRepository.deleteById(userSkillId);
    }

    @Override
    public List<UserSkill> getUserSkillsByUserId(Long userId) {
        return userSkillRepository.findByUserId(userId);
    }
}
