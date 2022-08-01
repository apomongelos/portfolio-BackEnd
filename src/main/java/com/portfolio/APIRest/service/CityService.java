/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.City;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface CityService {

    public List<City> getCities();

    public City getCity(Long id);

    public City saveCity(City city);

    public void deleteCity(Long id);
}
