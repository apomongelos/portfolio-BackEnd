/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.dto;

import java.io.Serializable;
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
public class EducationDTO implements Serializable {

    private Long id;
    
    private String degreeName;

    private String startingDate;

    private String completitionDate;
    
    private FacultyDTO faculty;
    
//    private UserDTO user;
}
