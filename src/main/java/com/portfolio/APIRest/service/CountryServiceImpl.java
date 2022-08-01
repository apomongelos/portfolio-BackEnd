/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Country;
import com.portfolio.APIRest.repository.CountryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class CountryServiceImpl implements CountryService {
    
    private final CountryRepository countryRepository;
    
    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
    
    @Override
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }
    
    @Override
    public Country getCountry(Long id) {
        return countryRepository.findById(id).orElse(null);
    }
    
    @Override
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }
    
    @Override
    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }
    
}
