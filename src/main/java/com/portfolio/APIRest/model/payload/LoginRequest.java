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
public class LoginRequest {

//    @NullOrNotBlank(message = "Login Username can be null but not blank")
    private String username;

//    @NullOrNotBlank(message = "Login Email can be null but not blank")
    private String email;

//    @NotNull(message = "Login password cannot be blank")
    private String password;

    public LoginRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public LoginRequest() {
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
}
