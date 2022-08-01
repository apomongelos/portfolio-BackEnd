/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Address;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface AddressService {

    public List<Address> getAddresses();

    public Address getAddress(Long id);

    public Address getAddressByUserId(Long userId);

    public Address saveAddress(Address address);

    public void deleteAddress(Long id);
}
