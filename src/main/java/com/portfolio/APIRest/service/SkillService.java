/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Skill;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface SkillService {

    public List<Skill> getSkills();

    public Skill getSkill(Long id);

    public Skill saveSkill(Skill skill);

    public void deleteSkill(Long id);
}
