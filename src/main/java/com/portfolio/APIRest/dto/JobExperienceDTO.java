/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.dto;

import com.portfolio.APIRest.model.City;
import com.portfolio.APIRest.model.Company;
import com.portfolio.APIRest.model.JobTitle;
import com.portfolio.APIRest.model.JobType;
import java.io.Serializable;
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
public class JobExperienceDTO implements Serializable {

    private Long id;

    private String startDate;

    private String endDate;

    private String profileDescription;

    private String imageUrl;

    private City city;

    private Company company;

    private JobTitle jobTitle;

    private JobType jobType;
}
