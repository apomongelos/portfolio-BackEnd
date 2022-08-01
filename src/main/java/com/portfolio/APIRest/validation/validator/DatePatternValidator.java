/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.validation.validator;

import com.portfolio.APIRest.validation.annotation.DatePattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Usuario
 */
public class DatePatternValidator implements ConstraintValidator<DatePattern, String> {

    @Override
    public void initialize(DatePattern constraintAnnotation) {

    }

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext constraintValidatorContext) {
        try {
            LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        } catch (Exception e){
            return true;
        }
        return true;
    }
}
