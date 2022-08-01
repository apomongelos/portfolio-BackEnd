/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Usuario
 */
@Entity(name = "UserSkill")
@Table(name = "users_skills")
@Getter
@Setter
public class UserSkill {

    @EmbeddedId
    private UserSkillId id = new UserSkillId();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @MapsId("skillId")
    @JoinColumn(name = "skill_id", referencedColumnName = "id")
    private Skill skill;

    @Column(name = "level", precision = 8, scale = 2)
    private Double level;

    public UserSkill() {
    }

    public UserSkill(UserSkillId id, User user, Skill skill, Double level) {
        this.id = id;
        this.user = user;
        this.skill = skill;
        this.level = level;
    }
}
