/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.validation.validator;

import com.portfolio.APIRest.validation.annotation.DateIsAfter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Usuario
 */
public class DateIsAfterValidator implements ConstraintValidator<DateIsAfter, String> {

    @Override
    public void initialize(DateIsAfter constraintAnnotation) {

    }

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext constraintValidatorContext) {
        try {
            LocalDate dateFromDTO = LocalDate.parse(dateStr);
            LocalDate dateNow = LocalDate.now();
            return dateNow.isAfter(dateFromDTO);
        } catch (DateTimeParseException e) {
            return true;
        } catch (Exception e){
            return true;
        }
    }
}
