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
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyInUseException extends RuntimeException {

    private final String resourceName;
    private final String fieldName;
    private final transient Object fieldValue;

    public ResourceAlreadyInUseException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s already in use with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
