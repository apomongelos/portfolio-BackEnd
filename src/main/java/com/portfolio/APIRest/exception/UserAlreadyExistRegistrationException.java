/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Usuario
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistRegistrationException extends RuntimeException {

    public UserAlreadyExistRegistrationException(String msg) {
        super(msg);
    }

    public UserAlreadyExistRegistrationException(String msg, Throwable cause) {
        super(msg);
    }
}
