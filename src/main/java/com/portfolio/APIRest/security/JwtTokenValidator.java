///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.portfolio.APIRest.security;
//
//import com.portfolio.APIRest.exception.InvalidTokenRequestException;
//import com.portfolio.APIRest.cache.LoggedOutJwtTokenCache;
//import com.portfolio.APIRest.event.OnUserLogoutSuccessEvent;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import java.util.Date;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
///**
// *
// * @author Usuario
// */
//@Component
//public class JwtTokenValidator {
//
//    private static final Logger logger = Logger.getLogger(JwtTokenValidator.class);
//    private final String jwtSecret;
//    private final LoggedOutJwtTokenCache loggedOutTokenCache;
//
//    @Autowired
//    public JwtTokenValidator(@Value("${app.jwt.secret}") String jwtSecret, LoggedOutJwtTokenCache loggedOutTokenCache) {
//        this.jwtSecret = jwtSecret;
//        this.loggedOutTokenCache = loggedOutTokenCache;
//    }
//
//    public boolean validateToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//
//        } catch (SignatureException ex) {
//            logger.error("Invalid JWT signature");
//            throw new InvalidTokenRequestException("JWT", authToken, "Incorrect signature");
//
//        } catch (MalformedJwtException ex) {
//            logger.error("Invalid JWT token");
//            throw new InvalidTokenRequestException("JWT", authToken, "Malformed jwt token");
//
//        } catch (ExpiredJwtException ex) {
//            logger.error("Expired JWT token");
//            throw new InvalidTokenRequestException("JWT", authToken, "Token expired. Refresh required");
//
//        } catch (UnsupportedJwtException ex) {
//            logger.error("Unsupported JWT token");
//            throw new InvalidTokenRequestException("JWT", authToken, "Unsupported JWT token");
//
//        } catch (IllegalArgumentException ex) {
//            logger.error("JWT claims string is empty.");
//            throw new InvalidTokenRequestException("JWT", authToken, "Illegal argument token");
//        }
////        validateTokenIsNotForALoggedOutDevice(authToken);
//        return true;
//    }
//
////    private void validateTokenIsNotForALoggedOutDevice(String authToken) {
////        OnUserLogoutSuccessEvent previouslyLoggedOutEvent = loggedOutTokenCache.getLogoutEventForToken(authToken);
////        if (previouslyLoggedOutEvent != null) {
////            String userEmail = previouslyLoggedOutEvent.getUserEmail();
////            Date logoutEventDate = previouslyLoggedOutEvent.getEventTime();
////            String errorMessage = String.format("Token corresponds to an already logged out user [%s] at [%s]. Please login again", userEmail, logoutEventDate);
////            throw new InvalidTokenRequestException("JWT", authToken, errorMessage);
////        }
////    }
//}
