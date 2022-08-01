/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Role;
import java.util.Collection;

/**
 *
 * @author Usuario
 */
public interface IRoleService {

    public Collection<Role> findAll();
}
