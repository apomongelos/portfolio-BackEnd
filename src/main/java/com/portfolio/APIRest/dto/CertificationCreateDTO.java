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
public class CertificationCreateDTO implements Serializable {

    @NotBlank(message = "name may not be empty")
    @Size(min = 2, message = "name should have at least 2 characters")
    private String name;

    @NotBlank(message = "start date may not be empty")
    @DatePattern(message = "start date may be a correct date or with the format yyyy-MM-dd")
    private String startDate;

    @DatePattern(message = "end date may be a correct date or with the format yyyy-MM-dd")
    private String endDate;
    
    @Size(min = 2, message = "url should have at least 2 characters")
    private String url;
    
    @NotNull(message = "user id may not be empty")
    private Long userId;
}
