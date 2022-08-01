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
public class EducationCreateDTO implements Serializable {

    @NotBlank(message = "degree name may not be empty")
    @Size(min = 2, message = "degree name should have at least 2 characters")
    private String degreeName;    

    @NotBlank(message = "starting date may not be empty")
    @DatePattern(message = "starting date may be a correct date or with the format yyyy-MM-dd")
    private String startingDate;

    @DatePattern(message = "completition date may be a correct date or with the format yyyy-MM-dd")
    private String completitionDate;

    @NotNull(message = "user id may not be empty")
    private Long userId;

    @NotNull(message = "faculty id may not be empty")
    private Long facultyId;
}
