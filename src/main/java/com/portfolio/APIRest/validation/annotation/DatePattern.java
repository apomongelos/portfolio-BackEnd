/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.validation.annotation;

import com.portfolio.APIRest.validation.validator.DatePatternValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author Usuario
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = DatePatternValidator.class)
@Documented
public @interface DatePattern {

    String message() default "The date was in incorrect format, please enter with yyyy-MM-dd";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
