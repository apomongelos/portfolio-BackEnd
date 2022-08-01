/*
 * To change this license header, choose License Headers in country Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.CountryCreateDTO;
import com.portfolio.APIRest.dto.CountryUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.Country;
import com.portfolio.APIRest.service.CountryService;
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
@RequestMapping(path = "api/countries")
public class CountryController {

    private final CountryService countryService;
    private final ModelMapper modelMapper;

    public CountryController(CountryService countryService, ModelMapper modelMapper) {
        this.countryService = countryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Country>> getCountries() {
        List<Country> countries = countryService.getCountries();
        return ResponseEntity.ok().body(countries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountry(@PathVariable Long id) {
        Country country = countryService.getCountry(id);
        if (country == null) {
            throw new NotFoundException("not found country with id: " + id);
        }

        return ResponseEntity.ok().body(country);
    }

    @PostMapping
    public ResponseEntity<Country> addNewCountry(@Valid @RequestBody CountryCreateDTO countryDTO) {
        Country newCountry = modelMapper.map(countryDTO, Country.class);

        Country newcountryCreated = countryService.saveCountry(newCountry);
        return new ResponseEntity<>(newcountryCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @Valid @RequestBody CountryUpdateDTO countryDTO) {
        Country countryToUpdate = countryService.getCountry(id);
        if (countryToUpdate == null) {
            throw new NotFoundException("not found country with id");
        }

        if (countryDTO.getName() != null) {
            countryToUpdate.setName(countryDTO.getName());
        }

        Country countryUpdated = countryService.saveCountry(countryToUpdate);
        return new ResponseEntity<>(countryUpdated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id) {
        try {
            countryService.deleteCountry(id);
        } catch (Exception e) {
            throw new NotFoundException("not found country with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully country with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
