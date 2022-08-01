/*
 * To change this license header, choose License Headers in company Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.CompanyCreateDTO;
import com.portfolio.APIRest.dto.CompanyUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.Company;
import com.portfolio.APIRest.service.CompanyService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping(path = "api/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    public CompanyController(CompanyService companyService, ModelMapper modelMapper) {
        this.companyService = companyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Company>> getCompanise() {
        List<Company> countries = companyService.getCompanies();
        return ResponseEntity.ok().body(countries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Long id) {
        Company company = companyService.getCompany(id);
        if (company == null) {
            throw new NotFoundException("not found company with id: " + id);
        }

        return ResponseEntity.ok().body(company);
    }

    @PostMapping
    public ResponseEntity<Company> addNewCompany(@Valid @RequestBody CompanyCreateDTO companyDTO) {
        Company newCompany = modelMapper.map(companyDTO, Company.class);

        Company newcompanyCreated = companyService.saveCompany(newCompany);
        return new ResponseEntity<>(newcompanyCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @Valid @RequestBody CompanyUpdateDTO companyDTO) {
        Company companyToUpdate = companyService.getCompany(id);
        if (companyToUpdate == null) {
            throw new NotFoundException("not found company with id");
        }

        if (companyDTO.getName() != null) {
            companyToUpdate.setName(companyDTO.getName());
        }

        Company companyUpdated = companyService.saveCompany(companyToUpdate);
        return new ResponseEntity<>(companyUpdated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        try {
            companyService.deleteCompany(id);
        } catch (Exception e) {
            throw new NotFoundException("not found company with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully company with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
