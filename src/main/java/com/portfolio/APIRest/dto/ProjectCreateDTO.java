/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.dto;

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
public class ProjectCreateDTO implements Serializable {

    @NotBlank(message = "name may not be empty")
    @Size(min = 2, message = "name should have at least 2 characters")
    private String name;

    @NotBlank(message = "description may not be empty")
    @Size(min = 2, message = "description should have at least 2 characters")
    private String description;

    @Size(min = 2, message = "url should have at least 2 characters")
    private String url;

    @Size(min = 2, message = "github url should have at least 2 characters")
    private String githubUrl;
    
    @Size(min = 2, message = "image url should have at least 2 characters")
    private String imageUrl;
    
    @NotNull(message = "user id may not be empty")
    private Long userId;
}
