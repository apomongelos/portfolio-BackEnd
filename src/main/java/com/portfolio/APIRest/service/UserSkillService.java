/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.UserSkill;
import com.portfolio.APIRest.model.UserSkillId;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface UserSkillService {

    public List<UserSkill> getUserSkills();

    public List<UserSkill> getUserSkillsByUserId(Long userId);

    public UserSkill getUserSkill(UserSkillId userSkillId);

    public UserSkill saveUserSkill(UserSkill userSkill);

    public void deleteUserSkill(UserSkillId userSkillId);
}
