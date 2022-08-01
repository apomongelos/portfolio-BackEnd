/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.dto;

import com.portfolio.APIRest.validation.annotation.DateIsAfter;
import com.portfolio.APIRest.validation.annotation.DatePattern;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
public class RegisterDTO implements Serializable {

    @NotBlank(message = "email may not be empty")
    @Email(message = "email should be a correct format")
    private String email;

    @NotBlank(message = "password may not be empty")
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;

    @NotBlank(message = "date of birth may not be empty")
    @DatePattern(message = "date of birth may be a correct date or with the format yyyy-MM-dd")
    @DateIsAfter(message = "date of birth may be at past date")
    private String dateOfBirth;

    @NotBlank(message = "first name may not be empty")
    @Size(min = 2, message = "first name should have at least 2 characters")
    private String firstName;

    @NotBlank(message = "last name may not be empty")
    @Size(min = 2, message = "last name should have at least 2 characters")
    private String lastName;

    @NotBlank(message = "username may not be empty")
    @Size(min = 2, message = "username should have at least 2 characters")
    private String username;
    
    @NotBlank(message = "about me may not be empty")
    @Size(min = 2, message = "about me should have at least 2 characters")
    private String aboutMe;
}
