/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.CityCreateDTO;
import com.portfolio.APIRest.dto.CityUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.exception.UnauthorizedException;
import com.portfolio.APIRest.model.City;
import com.portfolio.APIRest.model.Region;
import com.portfolio.APIRest.service.CityService;
import com.portfolio.APIRest.service.RegionService;
import com.portfolio.APIRest.service.TokenService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping(path = "api/cities")
@Slf4j
public class CityController {

    private final CityService cityService;
    private final RegionService regionService;
    private final ModelMapper modelMapper;
    private final TokenService tokenService;

    @Autowired
    public CityController(CityService cityService, ModelMapper modelMapper, RegionService regionService, TokenService tokenService) {
        this.cityService = cityService;
        this.modelMapper = modelMapper;
        this.regionService = regionService;
        this.tokenService = tokenService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<City>> getCities() {
        List<City> cities = cityService.getCities();
//        List<CityDTO> citysDTOs = citys
//                .stream()
//                .map(city -> modelMapper.map(city, CityDTO.class))
//                .collect(Collectors.toList());
        return ResponseEntity.ok().body(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCity(@PathVariable Long id) {
        City city = cityService.getCity(id);
        if (city == null) {
            throw new NotFoundException("not found city with id: " + id);
        }

//        CityDTO cityDTO = modelMapper.map(reg, CityDTO.class);
        return ResponseEntity.ok().body(city);
    }

    @PostMapping
    public ResponseEntity<City> addNewCity(@RequestHeader(value = "Authorization") String token,
            @Valid @RequestBody CityCreateDTO newCityDTO) {
//    public ResponseEntity<City> addNewCity(@Valid @RequestBody CityCreateDTO newCityDTO) {
        Boolean validToken = tokenService.validateToken(token);
        if (!validToken) {
            throw new UnauthorizedException("dont have permission for this action, please enter a valid token");
        }

        Region regionExist = regionService.getRegion(newCityDTO.getRegionId());
        if (regionExist == null) {
            throw new NotFoundException("not found region with id: " + newCityDTO.getRegionId());
        }

        City newCity = new City();
        newCity.setName(newCityDTO.getName());
        newCity.setRegion(regionExist);

        City newCityCreated = cityService.saveCity(newCity);
//        CityDTO newCityDTO = modelMapper.map(newCityCreated, CityDTO.class);
        return new ResponseEntity<>(newCityCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@RequestHeader(value = "Authorization") String token,
            @PathVariable Long id, @Valid @RequestBody CityUpdateDTO cityUpdateDTO) {
        Boolean validToken = tokenService.validateToken(token);
        if (!validToken) {
            throw new UnauthorizedException("dont have permission for this action, please enter a valid token");
        }

        City cityToUpdate = cityService.getCity(id);
        if (cityToUpdate == null) {
            throw new NotFoundException("not found city with id");
        }

        if (cityUpdateDTO.getRegionId() != null) {
            Region regExist = regionService.getRegion(cityUpdateDTO.getRegionId());
            if (regExist == null) {
                throw new NotFoundException("not found region wiht id: " + cityUpdateDTO.getRegionId());
            }
            cityToUpdate.setRegion(regExist);
        }
        if (cityUpdateDTO.getName() != null) {
            cityToUpdate.setName(cityUpdateDTO.getName());
        }

        City cityUpdated = cityService.saveCity(cityToUpdate);
//        CityDTO newCityDTO = modelMapper.map(regUpdated, CityDTO.class);
        return new ResponseEntity<>(cityUpdated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@RequestHeader(value = "Authorization") String token, @PathVariable Long id) {
        Boolean validToken = tokenService.validateToken(token);
        if (!validToken) {
            throw new UnauthorizedException("dont have permission for this action, please enter a valid token");
        }

        try {
            cityService.deleteCity(id);
        } catch (Exception e) {
            throw new NotFoundException("not found city with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully city with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
