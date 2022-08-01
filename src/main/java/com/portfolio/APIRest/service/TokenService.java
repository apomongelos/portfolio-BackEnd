/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.User;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public interface TokenService {

    public Map<String, String> createToken(User user);

    public Boolean validateToken(String token);
}
