/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.RegionCreateDTO;
import com.portfolio.APIRest.dto.RegionUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.Region;
import com.portfolio.APIRest.model.Country;
import com.portfolio.APIRest.service.RegionService;
import com.portfolio.APIRest.service.CountryService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping(path = "api/regions")
@Slf4j
public class RegionController {

    private final RegionService regionService;
    private final CountryService countryService;
    private final ModelMapper modelMapper;

    @Autowired
    public RegionController(RegionService regionService, ModelMapper modelMapper, CountryService countryService) {
        this.regionService = regionService;
        this.modelMapper = modelMapper;
        this.countryService = countryService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Region>> getRegions() {
        List<Region> regions = regionService.getRegions();
//        List<RegionDTO> regionsDTOs = regions
//                .stream()
//                .map(region -> modelMapper.map(region, RegionDTO.class))
//                .collect(Collectors.toList());
        return ResponseEntity.ok().body(regions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegion(@PathVariable Long id) {
        Region region = regionService.getRegion(id);
        if (region == null) {
            throw new NotFoundException("not found region with id: " + id);
        }

//        RegionDTO regionDTO = modelMapper.map(reg, RegionDTO.class);
        return ResponseEntity.ok().body(region);
    }

    @PostMapping
    public ResponseEntity<Region> addNewRegion(@Valid @RequestBody RegionCreateDTO newRegionDTO) {

        Country countryExist = countryService.getCountry(newRegionDTO.getCountryId());
        if (countryExist == null) {
            throw new NotFoundException("not found country with id: " + newRegionDTO.getCountryId());
        }

        Region newRegion = new Region();
        newRegion.setName(newRegionDTO.getName());
        newRegion.setCountry(countryExist);

        Region newRegionCreated = regionService.saveRegion(newRegion);
//        RegionDTO newRegionDTO = modelMapper.map(newRegionCreated, RegionDTO.class);
        return new ResponseEntity<>(newRegionCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> updateRegion(@PathVariable Long id, @Valid @RequestBody RegionUpdateDTO regUpdateDTO) {
        Region regToUpdate = regionService.getRegion(id);
        if (regToUpdate == null) {
            throw new NotFoundException("not found region with id");
        }

        if (regUpdateDTO.getCountryId() != null) {
            Country facExist = countryService.getCountry(regUpdateDTO.getCountryId());
            if (facExist == null) {
                throw new NotFoundException("not found country wiht id: " + regUpdateDTO.getCountryId());
            }
            regToUpdate.setCountry(facExist);
        }
        if (regUpdateDTO.getName() != null) {
            regToUpdate.setName(regUpdateDTO.getName());
        }

        Region regUpdated = regionService.saveRegion(regToUpdate);
//        RegionDTO newRegionDTO = modelMapper.map(regUpdated, RegionDTO.class);
        return new ResponseEntity<>(regUpdated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegion(@PathVariable Long id) {
        try {
            regionService.deleteRegion(id);
        } catch (Exception e) {
            throw new NotFoundException("not found region with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully region with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
