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
public class JobExperienceCreateDTO implements Serializable {

    @NotBlank(message = "start date may not be empty")
    @DatePattern(message = "start date may be a correct date or with the format yyyy-MM-dd")
    private String startDate;

    @DatePattern(message = "end date may be a correct date or with the format yyyy-MM-dd")
    private String endDate;

    @NotBlank(message = "profile description may not be empty")
    @Size(min = 2, message = "profile description should have at least 2 characters")
    private String profileDescription;

    @Size(min = 2, message = "image url should have at least 2 characters")
    private String imageUrl;

    @NotNull(message = "user id may not be empty")
    private Long userId;

    @NotNull(message = "city id may not be empty")
    private Long cityId;

    @NotNull(message = "company id may not be empty")
    private Long companyId;

    @NotNull(message = "job type id may not be empty")
    private Long jobTypeId;

    @NotNull(message = "job title id may not be empty")
    private Long jobTitleId;

}
