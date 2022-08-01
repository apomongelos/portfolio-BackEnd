/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.portfolio.APIRest.model.User;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Value("${security.jwt.secret}")
    private String key;
    
    @Override
    public Map<String, String> createToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String access_token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000))
                .withClaim("roles", "admin")
                .sign(algorithm);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        return tokens;
    }

    @Override
    public Boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token.split(" ")[1]);
            String email = decodedJWT.getSubject();
            return email != null;
        } catch (Exception e) {
            return false;
        }
    }

}
