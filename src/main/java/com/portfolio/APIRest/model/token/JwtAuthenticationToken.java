///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.portfolio.APIRest.model.token;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//
///**
// *
// * @author Usuario
// */
//public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
//
//    private String token;
//
//    public JwtAuthenticationToken(Object principal, Object credentials, String token) {
//        super(null, null);
//        this.token = token;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    @Override
//    public Object getCredentials() {
//        return null;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return null;
//    }
//}
