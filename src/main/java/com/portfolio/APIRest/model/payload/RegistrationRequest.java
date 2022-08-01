/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.model.payload;

//import com.portfolio.APIRest.validation.annotation.NullOrNotBlank;
//import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Usuario
 */
public class RegistrationRequest {

//    @NullOrNotBlank(message = "Registration username can be null but not blank")
    private String username;

//    @NullOrNotBlank(message = "Registration email can be null but not blank")
    private String email;

//    @NotNull(message = "Registration password cannot be null")
    private String password;

//    @NotNull(message = "Specify whether the user has to be registered as an admin or not")
    private Boolean registerAsAdmin;

    public RegistrationRequest(String username, String email,
            String password, Boolean registerAsAdmin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.registerAsAdmin = registerAsAdmin;
    }

    public RegistrationRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRegisterAsAdmin() {
        return registerAsAdmin;
    }

    public void setRegisterAsAdmin(Boolean registerAsAdmin) {
        this.registerAsAdmin = registerAsAdmin;
    }
}
