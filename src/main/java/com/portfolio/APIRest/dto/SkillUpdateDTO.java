/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.dto;

import java.io.Serializable;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Usuario
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillUpdateDTO implements Serializable {

    @Size(min = 2, message = "name should have at least 2 characters")
    private String name;
    
    @Size(min = 2, message = "image url should have at least 2 characters")
    private String imageUrl;
}
