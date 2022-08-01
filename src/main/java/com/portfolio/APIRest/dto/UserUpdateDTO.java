/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.dto;

import com.portfolio.APIRest.validation.annotation.DatePattern;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class UserUpdateDTO implements Serializable {

    @Size(min = 2, message = "first name should have at least 2 characters")
    private String firstName;
    
    @Size(min = 2, message = "last name should have at least 2 characters")
    private String lastName;

    @DatePattern(message = "date of birth may be a correct date or with the format yyyy-MM-dd")
    private String dateOfBirth;

    @Size(min = 2, message = "email should have at least 2 characters")
    private String email;
    
    @Size(min = 2, message = "about me should have at least 2 characters")
    private String aboutMe;
    
    @Size(min = 2, message = "username should have at least 2 characters")
    private String username;   
    
    private Long userId;    
}
