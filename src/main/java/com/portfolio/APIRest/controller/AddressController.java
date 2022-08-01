/*
 * To change this license header, choose License Headers in Address Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.AddressCreateDTO;
import com.portfolio.APIRest.dto.AddressDTO;
import com.portfolio.APIRest.dto.AddressUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.Address;
import com.portfolio.APIRest.model.City;
import com.portfolio.APIRest.model.User;
import com.portfolio.APIRest.service.AddressService;
import com.portfolio.APIRest.service.CityService;
import com.portfolio.APIRest.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping(path = "api/addresses")
public class AddressController {

    private final AddressService addressService;
    private final CityService cityService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressController(AddressService addressService, ModelMapper modelMapper, UserService userService, CityService cityService) {
        this.addressService = addressService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.cityService = cityService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<AddressDTO>> getAddresses() {
        List<Address> addresss = addressService.getAddresses();

        List<AddressDTO> addressDTOs = addresss
                .stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(addressDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Long id) {
        Address address = addressService.getAddress(id);
        if (address == null) {
            throw new NotFoundException("not found address with id: " + id);
        }
        AddressDTO addressDTO = modelMapper.map(address, AddressDTO.class);
        return ResponseEntity.ok().body(addressDTO);
    }

    @GetMapping("/")
    public ResponseEntity<AddressDTO> getAddressByUserEmail(@RequestParam(required = false) String email) {
        User usrExist = userService.getUserByEmail(email);

        Address address = addressService.getAddressByUserId(usrExist.getId());
        if (address == null) {
            throw new NotFoundException("not found address with user email: " + email);
        }
        AddressDTO addressDTO = modelMapper.map(address, AddressDTO.class);
        return ResponseEntity.ok().body(addressDTO);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> addNewAddress(@Valid @RequestBody AddressCreateDTO addressDTO) {
        User usrExist = userService.getUser(addressDTO.getUserId());
        if (usrExist == null) {
            throw new NotFoundException("not found user with id: " + addressDTO.getUserId());
        }
        City cityExist = cityService.getCity(addressDTO.getCityId());
        if (cityExist == null) {
            throw new NotFoundException("not found city with id: " + addressDTO.getCityId());
        }

        Address newAddress = new Address();
        newAddress.setStreetName(addressDTO.getStreetName());

        newAddress.setUser(usrExist);
        newAddress.setCity(cityExist);

        Address newAddressCreated = addressService.saveAddress(newAddress);
        AddressDTO newAddressDTO = modelMapper.map(newAddressCreated, AddressDTO.class);
        return new ResponseEntity<>(newAddressDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @Valid @RequestBody AddressUpdateDTO addressDTO) {

        Address addrToUpdate = addressService.getAddress(id);

        if (addrToUpdate == null) {
            throw new NotFoundException("not found address with id");
        }

        if (addressDTO.getUserId() != null) {
            User usrExist = userService.getUser(addressDTO.getUserId());
            if (usrExist == null) {
                throw new NotFoundException("not found user with id: " + addressDTO.getUserId());
            }
            addrToUpdate.setUser(usrExist);
        }
        if (addressDTO.getCityId() != null) {
            City cityExist = cityService.getCity(addressDTO.getCityId());
            if (cityExist == null) {
                throw new NotFoundException("not found city with id: " + addressDTO.getCityId());
            }
            addrToUpdate.setCity(cityExist);
        }

        if (addressDTO.getStreetName() != null) {
            addrToUpdate.setStreetName(addressDTO.getStreetName());
        }

        Address addressUpdated = addressService.saveAddress(addrToUpdate);
        AddressDTO newAddressDTO = modelMapper.map(addressUpdated, AddressDTO.class);
        return new ResponseEntity<>(newAddressDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        try {
            addressService.deleteAddress(id);
        } catch (Exception e) {
            throw new NotFoundException("not found address with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully address with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
