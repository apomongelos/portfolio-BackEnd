///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.portfolio.APIRest.cache;
//
//import com.portfolio.APIRest.event.OnUserLogoutSuccessEvent;
//import java.time.Instant;
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import com.portfolio.APIRest.security.JwtTokenProvider;
////import net.jodah.expiringmap.ExpiringMap;
//
///**
// *
// * @author Usuario
// */
//@Component
//public class LoggedOutJwtTokenCache {
//
//    private static final Logger logger = Logger.getLogger(LoggedOutJwtTokenCache.class);
//
////    private final ExpiringMap<String, OnUserLogoutSuccessEvent> tokenEventMap;
//    private final JwtTokenProvider tokenProvider;
//
//    @Autowired
//    public LoggedOutJwtTokenCache(@Value("${app.cache.logoutToken.maxSize}") int maxSize, JwtTokenProvider tokenProvider) {
//        this.tokenProvider = tokenProvider;
////        this.tokenEventMap = ExpiringMap.builder()
////                .variableExpiration()
////                .maxSize(maxSize)
////                .build();
//    }
//
////    public void markLogoutEventForToken(OnUserLogoutSuccessEvent event) {
////        String token = event.getToken();
////        if (tokenEventMap.containsKey(token)) {
////            logger.info(String.format("Log out token for user [%s] is already present in the cache", event.getUserEmail()));
////
////        } else {
////            Date tokenExpiryDate = tokenProvider.getTokenExpiryFromJWT(token);
////            long ttlForToken = getTTLForToken(tokenExpiryDate);
////            logger.info(String.format("Logout token cache set for [%s] with a TTL of [%s] seconds. Token is due expiry at [%s]", event.getUserEmail(), ttlForToken, tokenExpiryDate));
////            tokenEventMap.put(token, event, ttlForToken, TimeUnit.SECONDS);
////        }
////    }
////
////    public OnUserLogoutSuccessEvent getLogoutEventForToken(String token) {
////        return tokenEventMap.get(token);
////    }
////
////    private long getTTLForToken(Date date) {
////        long secondAtExpiry = date.toInstant().getEpochSecond();
////        long secondAtLogout = Instant.now().getEpochSecond();
////        return Math.max(0, secondAtExpiry - secondAtLogout);
////    }
//}
