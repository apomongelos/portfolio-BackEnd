/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Usuario
 */
@Entity(name = "Education")
@Table(name = "educations")
@Getter
@Setter
public class Education {

    @Id
    @SequenceGenerator(name = "educations_sequence", sequenceName = "educations_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "educations_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "degree_name", nullable = false)
    private String degreeName;

    @Column(name = "starting_date", nullable = false)
    private LocalDate startingDate;

    @Column(name = "completition_date", nullable = true)
    private LocalDate completitionDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    private Faculty faculty;

    public Education() {
    }

    public Education(Long id, String name, String description) {
        this.id = id;
        this.degreeName = name;
    }
}
