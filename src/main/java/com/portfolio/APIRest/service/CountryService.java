/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Country;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface CountryService {

    public List<Country> getCountries();

    public Country getCountry(Long id);

    public Country saveCountry(Country country);

    public void deleteCountry(Long id);
}
