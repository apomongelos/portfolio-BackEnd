/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

/**
 *
 * @author Usuario
 */
@Embeddable
@Data
public class UserJobTitleId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "job_title_id")
    private Long jobTitleId;
}
